package com.behnam.university.service.implemention;


import com.behnam.university.dto.StudentDto;
import com.behnam.university.mapper.generic_converter.Converter;
import com.behnam.university.model.College;
import com.behnam.university.model.Course;
import com.behnam.university.model.Professor;
import com.behnam.university.model.Student;
import com.behnam.university.repository.CollegeRepository;
import com.behnam.university.repository.CourseRepository;
import com.behnam.university.repository.ProfessorRepository;
import com.behnam.university.repository.StudentRepository;
import com.behnam.university.service.interfaces.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

/**
 * @author Behnam Si
 */

@Service("studentServiceImp")
@Primary
public class StudentServiceImp implements StudentService {

    private final StudentRepository repository;
    private final CollegeRepository collegeRepository;
    private final CourseRepository courseRepository;
    private final ProfessorRepository professorRepository;
    private final Converter<Student, StudentDto> studentToStudentDtoConverter;
    private final Converter<StudentDto, Student> studentDtoToStudentConverter;

    @Autowired
    public StudentServiceImp(
            StudentRepository repository,
            CollegeRepository collegeRepository,
            CourseRepository courseRepository,
            ProfessorRepository professorRepository,
            Converter<Student, StudentDto> studentToStudentDtoConverter,
            Converter<StudentDto, Student> studentDtoToStudentConverter) {
        this.repository = repository;
        this.collegeRepository = collegeRepository;
        this.courseRepository = courseRepository;
        this.professorRepository = professorRepository;
        this.studentToStudentDtoConverter = studentToStudentDtoConverter;
        this.studentDtoToStudentConverter = studentDtoToStudentConverter;
    }

    @Override
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
        List<StudentDto> result = new ArrayList<>();
        for (Student s :
                studentPage.getContent()) {
            StudentDto dto = new StudentDto();
            studentToStudentDtoConverter.convert(s, dto);
            result.add(dto);
        }
        return result;
//        StudentMapper mapper = new StudentMapper();
//        return studentPage
//                .getContent()
//                .stream()
//                .map(mapper::studentToDto)
//                .collect(toList());
    }

    @Override
    public StudentDto getStudent(Long studentUniId) {
        if (!repository.existsStudentByUniversityId(studentUniId)) {
            throw new IllegalStateException("invalid uni id");
        }
        Student student = repository.findStudentByUniversityId(studentUniId);
        StudentDto studentDTO = new StudentDto();
        studentToStudentDtoConverter.convert(student, studentDTO);
        return studentDTO;
    }

    @Override
    public Student addStudent(StudentDto studentDto, String collegeName) {
        if (collegeName != null) {
            if (!collegeRepository.existsCollegeByCollegeName(collegeName)) {
                throw new IllegalStateException("invalid college name");
            }
            College college = collegeRepository
                    .findCollegeByCollegeName(collegeName);

            if (repository.existsStudentByUniversityId(studentDto.getUniversityId())) {
                throw new IllegalStateException("university id is taken");
            }
            if (repository.existsStudentByNationalId(studentDto.getNationalId())
                    || professorRepository.existsProfessorByNationalId(studentDto.getNationalId())) {
                throw new IllegalStateException("national id is taken");
            }
            // mapping to entity
            Student student = new Student();
//            StudentMapper mapper = new StudentMapper();
//            student = mapper.dtoTOStudent(studentDto);
            studentDtoToStudentConverter.convert(studentDto, student);
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
    @Override
    @Transactional
    public void deleteStudentByUniId(Long uniId) {
        if (!repository.existsStudentByUniversityId(uniId)) {
            throw new IllegalStateException("invalid university id.");
        }
        repository.deleteStudentByUniversityId(uniId);
    }

    @Override
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

    @Override
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

    @Override
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
    @Override
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

    @Override
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

}
