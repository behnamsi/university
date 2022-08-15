package com.behnam.university.service;


import com.behnam.university.dto.StudentDto;
import com.behnam.university.mapper.StudentMapper;
import com.behnam.university.model.College;
import com.behnam.university.model.Course;
import com.behnam.university.model.Professor;
import com.behnam.university.model.Student;
import com.behnam.university.repository.CollegeRepository;
import com.behnam.university.repository.CourseRepository;
import com.behnam.university.repository.ProfessorRepository;
import com.behnam.university.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

import static java.util.stream.Collectors.toList;

@Service
public class StudentService {

    private final StudentRepository repository;

    private final CollegeRepository collegeRepository;

    private final CourseRepository courseRepository;
    private final ProfessorRepository professorRepository;

    @Autowired
    public StudentService(StudentRepository repository, CollegeRepository collegeRepository, CourseRepository courseRepository, ProfessorRepository professorRepository) {
        this.repository = repository;
        this.collegeRepository = collegeRepository;
        this.courseRepository = courseRepository;
        this.professorRepository = professorRepository;
    }

    public List<StudentDto> getAllStudents(Integer limit, Integer page) {
        // limit and page filter
        if (limit == null) limit = 3;
        if (page == null) page = 0;
        else page -= 1;
        if (limit > 100) throw new IllegalStateException("limit should not be more than 100");
        // paging nad sorting data
        Pageable studentPageable =
                PageRequest.of(page, limit, Sort.by("lastName").descending());
        Page<Student> studentPage = repository.findAll(studentPageable);
        // mapping data to dto
        StudentMapper mapper = new StudentMapper();
        return studentPage
                .getContent()
                .stream()
                .map(mapper::studentToDto)
                .collect(toList());
    }


    public Student addStudent(StudentDto studentDto, String collegeName) {
        if (collegeName != null) {
            if (!collegeRepository.existsCollegeByCollegeName(collegeName)) {
                throw new IllegalStateException("invalid college name");
            }
            College college = collegeRepository
                    .findCollegeByCollegeName(collegeName);
            // mapping to entity
            Student student;
            StudentMapper mapper = new StudentMapper();
            student = mapper.dtoTOStudent(studentDto);
            if (repository.existsStudentByUniversityId(studentDto.getUniversityId())) {
                throw new IllegalStateException("university id is taken");
            }
            if (repository.existsStudentByNationalId(studentDto.getNationalId())
                    || professorRepository.existsProfessorByNationalId(studentDto.getNationalId())) {
                throw new IllegalStateException("national id is taken");
            }

            student.setStudentCollege(college);
            repository.save(student);
            return student;
        } else {
            throw new IllegalStateException("college id can not be null");
        }
    }

//    @Transactional
//    public void deleteStudent(Long id) {
//        if (!repository.existsById(id)) {
//            throw new IllegalStateException("student with this Id does not exists.");
//        }
//        repository.deleteById(id);
//    }

    @Transactional
    public void deleteStudentByUniId(Long uniId) {
        if (!repository.existsStudentByUniversityId(uniId)) {
            throw new IllegalStateException("invalid university id.");
        }
        repository.deleteStudentByUniversityId(uniId);
    }

    @Transactional
    public Student updateStudent(Long uniId, String first_name, String last_name, List<String> courses,
                                 Long nationalId) {
        if (!repository.existsStudentByUniversityId(uniId)) {
            throw new IllegalStateException("invalid university id");
        }
        Student student = repository.findStudentByUniversityId(uniId);


        if (repository.existsStudentByNationalId(nationalId)) {
            throw new IllegalStateException("national id has a owner");
        }

        if (first_name != null && first_name.length() > 0 &&
                !Objects.equals(student.getFirstName(), first_name)) {
            student.setFirstName(first_name);
        }
        if (last_name != null && last_name.length() > 0 &&
                !Objects.equals(student.getLastName(), last_name)) {
            student.setLastName(last_name);
        }
        if (!courses.isEmpty()) {
            List<Course> courses1 = new ArrayList<>();
            if (student.getStudentCourses() != null) courses1.addAll(student.getStudentCourses());
            List<Professor> professorsOfStudent = new ArrayList<>();
            for (String courseName : courses) {
                if (!courseRepository.existsCourseByCourseName(courseName)) {
                    throw new IllegalStateException("invalid course name");
                }
                Course course = courseRepository.findCourseByCourseName(courseName);
                professorsOfStudent.add(course.getProfessor());
                courses1.add(course);
                student.setStudentCourses(courses1);
                student.setProfessorsOfStudent(professorsOfStudent);
            }
        }
        if (nationalId != null &&
                !Objects.equals(student.getLastName(), last_name)) {
            student.setNationalId(nationalId);
        }
        return student;
    }

    @Transactional
    public List<String> getStudentCourses(Long uniId) {
        if (!repository.existsStudentByUniversityId(uniId)) {
            throw new IllegalStateException("invalid uni id for the student");
        }
        Student student = repository.findStudentByUniversityId(uniId);
        List<String> courses = new ArrayList<>();
        for (Course course : student.getStudentCourses()) {
            courses.add(course.getCourseName());
        }
        return courses;
    }

    @Transactional
    public void addScoreCourse(Long uniId, String courseName, Double score) {

        if (!repository.existsStudentByUniversityId(uniId)) {
            throw new IllegalStateException("invalid university id");
        }

        if (!courseRepository.existsCourseByCourseName(courseName)) {
            throw new IllegalStateException("invalid course name");
        }


        Student student = repository.findStudentByUniversityId(uniId);
        List<Course> courseList = student.getStudentCourses();
        boolean courseFlag = false;
        for (Course course : courseList) {
            if (course.getCourseName().equals(courseName)) {
                courseFlag = true;
                break;
            }
        }
        if (!courseFlag) {
            throw new IllegalStateException("course not defined for this student");
        }

        Map<String, Double> scoreCourse = student.getScores();
        scoreCourse.put(courseName, score);
        student.setScores(scoreCourse);
    }

    // delete a course of a student
    @Transactional
    public void deleteStudentCourse(Long uniId, String courseName) {
        //TODO drop the professor of that course.
        if (!repository.existsStudentByUniversityId(uniId)) {
            throw new IllegalStateException("invalid university id");
        }
        if (!courseRepository.existsCourseByCourseName(courseName)) {
            throw new IllegalStateException("invalid course name");
        }
        Student student = repository.findStudentByUniversityId(uniId);
        boolean courseFlag = false;
        List<Course> courseList = new LinkedList<>(student.getStudentCourses());
        for (int i = 0; i < courseList.size(); i++) {
            if (courseList.get(i).getCourseName().equals(courseName)) {
                courseFlag = true;
                courseList.remove(i);
                break;
            }
        }
        if (!courseFlag) {
            throw new IllegalStateException("this student does not have this course");
        }
        student.setStudentCourses(courseList);
        Map<String, Double> studentScoresMap = student.getScores();
        for (String courseName1 : studentScoresMap.keySet()) {
            if (courseName1.equals(courseName)) {
                studentScoresMap.remove(courseName);
                break;
            }
        }
        student.setScores(studentScoresMap);
    }

    @Transactional
    public Double getStudentAverage(Long uniID) {
        if (!repository.existsStudentByUniversityId(uniID)) {
            throw new IllegalStateException("invalid uni id");
        }
        Student student = repository.findStudentByUniversityId(uniID);

        List<Course> courses = new ArrayList<>(student.getStudentCourses());
        Map<String, Double> scores = new HashMap<>(student.getScores());
        if (courses.size() == 0) {
            throw new IllegalStateException("no courses taken");
        }
        if (courses.size() > scores.size()) {
            throw new IllegalStateException("all course`s results must be present.");
        }
        int numOfUnits = 0;
        double sum = 0, result;
        for (Course course : courses) {
            sum += scores.get(course.getCourseName()) * course.getUnitNumber();
            numOfUnits += course.getUnitNumber();
        }
        result = sum / numOfUnits;
        return result;
    }

    public StudentDto getStudent(Long studentUniId) {
        if (!repository.existsStudentByUniversityId(studentUniId)) {
            throw new IllegalStateException("invalid uni id");
        }
        Student student = repository.findStudentByUniversityId(studentUniId);
        StudentDto studentDTO = new StudentDto();
        StudentMapper mapper = new StudentMapper();
        studentDTO = mapper.studentToDto(student);
        return studentDTO;
    }
}
