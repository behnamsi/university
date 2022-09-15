package com.behnam.university.service.implemention;


import com.behnam.university.dto.create.StudentCreateDto;
import com.behnam.university.dto.detail.StudentDetailDto;
import com.behnam.university.dto.list.StudentListDto;
import com.behnam.university.dto.studentCourses.StudentAddCourseDto;
import com.behnam.university.dto.studentCourses.StudentCourseScoreDto;
import com.behnam.university.dto.update.StudentUpdateDto;
import com.behnam.university.mapper.generic_converter.Converter;
import com.behnam.university.mapper.static_mapper.StaticMapper;
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
import org.springframework.validation.annotation.Validated;

import javax.transaction.Transactional;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * @author Behnam Si
 */

@Service("studentServiceImp")
@Primary
@Validated
public class StudentServiceImp implements StudentService {

    protected final StudentRepository repository;
    protected final CollegeRepository collegeRepository;
    protected final CourseRepository courseRepository;
    protected final ProfessorRepository professorRepository;
    protected final Converter<Student, StudentCreateDto> studentToStudentDtoConverter;
    protected final Converter<StudentCreateDto, Student> studentDtoToStudentConverter;

    @Autowired
    public StudentServiceImp(
            StudentRepository repository,
            CollegeRepository collegeRepository,
            CourseRepository courseRepository,
            ProfessorRepository professorRepository,
            Converter<Student, StudentCreateDto> studentToStudentDtoConverter,
            Converter<StudentCreateDto, Student> studentDtoToStudentConverter) {
        this.repository = repository;
        this.collegeRepository = collegeRepository;
        this.courseRepository = courseRepository;
        this.professorRepository = professorRepository;
        this.studentToStudentDtoConverter = studentToStudentDtoConverter;
        this.studentDtoToStudentConverter = studentDtoToStudentConverter;
    }

    @Override
    public List<StudentCreateDto> getAllStudents(Integer limit, Integer page) {
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
        List<StudentCreateDto> result = new ArrayList<>();
        for (Student s :
                studentPage.getContent()) {
            StudentCreateDto dto = new StudentCreateDto();
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
    public List<StudentListDto> getAllStudents(Pageable pageable) {
        List<Student> students = repository.findAll(pageable).getContent();
        List<StudentListDto> studentDetailDTOS = new ArrayList<>();
        for (Student s :
                students) {
            StudentListDto dto = new StudentListDto();
            try {
                StaticMapper.mapper(s, dto);
                studentDetailDTOS.add(dto);
            } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return studentDetailDTOS;
    }

    @Override
    public StudentDetailDto getStudent(Long studentUniId) {
        if (!repository.existsStudentByUniversityId(studentUniId)) {
            throw new IllegalStateException("invalid uni id");
        }
        Student student = repository.findStudentByUniversityId(studentUniId);
        StudentDetailDto studentDetailDto = new StudentDetailDto();
        try {
            StaticMapper.mapper(student, studentDetailDto);
        } catch (InvocationTargetException | IllegalAccessException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return studentDetailDto;
    }

    @Override
    public StudentCreateDto addStudent(StudentCreateDto studentCreateDto, String collegeName) {
        if (collegeName != null) {
            if (!collegeRepository.existsCollegeByCollegeName(collegeName)) {
                throw new IllegalStateException("invalid college name");
            }
            College college = collegeRepository
                    .findCollegeByCollegeName(collegeName);

            if (repository.existsStudentByUniversityId(studentCreateDto.getUniversityId())) {
                throw new IllegalStateException("university id is taken");
            }
            if (repository.existsStudentByNationalId(studentCreateDto.getNationalId())
                    || professorRepository.existsProfessorByNationalId(studentCreateDto.getNationalId())) {
                throw new IllegalStateException("national id is taken");
            }
            // mapping to entity
            Student student = new Student();
//            StudentMapper mapper = new StudentMapper();
//            student = mapper.dtoTOStudent(studentDto);
            studentDtoToStudentConverter.convert(studentCreateDto, student);
            student.setStudentCollege(college);
            repository.save(student);
            return studentCreateDto;
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
    public Long deleteStudentByUniId(Long uniId) {
        if (!repository.existsStudentByUniversityId(uniId)) {
            throw new IllegalStateException("invalid university id.");
        }
        repository.deleteStudentByUniversityId(uniId);
        return uniId;
    }

    @Override
    @Transactional
    public Student updateStudent(Long uniId, String first_name, String last_name, List<String> courses,
                                 Long nationalId) {
//        if (!repository.existsStudentByUniversityId(uniId)) {
//            throw new IllegalStateException("invalid university id");
//        }
//        Student student = repository.findStudentByUniversityId(uniId);
//
//
//        if (repository.existsStudentByNationalId(nationalId) &&
//                professorRepository.existsProfessorByNationalId(nationalId)) {
//            throw new IllegalStateException("national id has a owner");
//        }
//
//        if (first_name != null && first_name.length() > 2 &&
//                first_name.length() < 20 &&
//                !Objects.equals(student.getFirstName(), first_name)) {
//            student.setFirstName(first_name);
//        }
//        if (last_name != null && last_name.length() > 2 &&
//                last_name.length() < 20 &&
//                !Objects.equals(student.getLastName(), last_name)) {
//            student.setLastName(last_name);
//        }
//        if (!courses.isEmpty()) {
//            List<Course> courses1 = new ArrayList<>();
//            if (student.getStudentCourses() != null) courses1.addAll(student.getStudentCourses());
//            List<Professor> professorsOfStudent = new ArrayList<>();
//            for (String courseName : courses) {
//                if (!courseRepository.existsCourseByCourseName(courseName)) {
//                    throw new IllegalStateException("invalid course name");
//                }
//                Course course = courseRepository.findCourseByCourseName(courseName);
//                professorsOfStudent.add(course.getProfessor());
//                courses1.add(course);
//                student.setStudentCourses(courses1);
//                student.setProfessorsOfStudent(professorsOfStudent);
//            }
//        }
//        if (nationalId != null) {
//            student.setNationalId(nationalId);
//        }
//        return student;
        return null;
    }

    @Override
    @Transactional
    public StudentUpdateDto updateStudent(Long uniId, StudentUpdateDto dto) {
        if (!repository.existsStudentByUniversityId(uniId)) {
            throw new IllegalStateException("student does not exists.");
        }
        if (repository.existsStudentByNationalId(dto.getNationalId()) ||
                professorRepository.existsProfessorByNationalId(dto.getNationalId())) {
            throw new IllegalStateException("national id has taken");
        }
        Student student = repository.findStudentByUniversityId(uniId);
        if (dto.getFirstName() != null) {
            student.setFirstName(dto.getFirstName());
        }
        if (dto.getLastName() != null) {
            student.setLastName(dto.getLastName());
        }


        // adding course/courses to the student with its professor
        if (dto.getCourses() != null) {
            if (!dto.getCourses().isEmpty()) {
                for (String courseName :
                        dto.getCourses()) {
                    if (!courseRepository.existsCourseByCourseName(courseName)) {
                        throw new IllegalStateException("the course with name" + courseName + "is not available");
                    }
                    Course course = courseRepository.findCourseByCourseName(courseName);
                    Professor professor = course.getProfessor();
                    if (!student.getProfessorsOfStudent().contains(professor)) {
                        student.getProfessorsOfStudent().add(professor);

                    }
                    if (!student.getStudentCourses().contains(course)) {
                        student.getStudentCourses().add(course);
                    } else throw new IllegalStateException("this student already has this course");
                }
//            student.setStudentCourses(courses);
            }
        }
        // end of course


        if (dto.getNationalId() != null) {
            student.setNationalId(dto.getNationalId());
        }
        return dto;
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

    @Override
    @Transactional
    public void addScoreCourse(Long uniId, StudentCourseScoreDto dto) {
        if (!repository.existsStudentByUniversityId(uniId))
            throw new IllegalStateException("invalid university id");

        if (!courseRepository.existsCourseByCourseName(dto.getCourseName()))
            throw new IllegalStateException("invalid course name");

        Student student = repository.findStudentByUniversityId(uniId);
        List<Course> courseList = student.getStudentCourses();
        boolean courseFlag = false;
        for (Course course : courseList) {
            if (course.getCourseName().equals(dto.getCourseName())) {
                courseFlag = true;
                break;
            }
        }
        if (!courseFlag) {
            throw new IllegalStateException("course not defined for this student");
        }

        Map<String, Double> scoreCourse = student.getScores();
        scoreCourse.put(dto.getCourseName(), dto.getScore());
        student.setScores(scoreCourse);
    }

    // delete a course of a student
    @Override
    @Transactional
    public void deleteStudentCourse(Long uniId, String courseName) {
        if (!repository.existsStudentByUniversityId(uniId)) {
            throw new IllegalStateException("invalid university id");
        }
        if (!courseRepository.existsCourseByCourseName(courseName)) {
            throw new IllegalStateException("invalid course name");
        }
        Student student = repository.findStudentByUniversityId(uniId);
        boolean courseFlag = false;
        short professorCount = 0;
        List<Course> courseList = new LinkedList<>(student.getStudentCourses());
        List<Professor> professors = new LinkedList<>(student.getProfessorsOfStudent());
        for (int i = 0; i < courseList.size(); i++) {
            // find the special course
            if (courseList.get(i).getCourseName().equals(courseName)) {
                // we found it
                courseFlag = true;
                for (int j = 0; j < courseList.size(); j++) {
                    if (courseList.get(i).getProfessor().equals(courseList.get(j).getProfessor())) {
                        professorCount++;
                    }
                }
                if (professorCount <= 1) {
                    professors.remove(courseList.get(i).getProfessor());
                }
                courseList.remove(i);
                break;
            }
        }
        if (!courseFlag) {
            throw new IllegalStateException("this student does not have this course");
        }
        student.setStudentCourses(courseList);
        student.setProfessorsOfStudent(professors);
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

    @Override
    @Transactional
    public void addCourseToEnrolledCourse(Long uniId, StudentAddCourseDto dto) {
        if (!repository.existsStudentByUniversityId(uniId))
            throw new IllegalStateException("invalid university id");

        if (!courseRepository.existsCourseByCourseName(dto.getCourseName())) {
            throw new IllegalStateException("the course with name" + dto.getCourseName() + "is not available");
        }
        Student student = repository.findStudentByUniversityId(uniId);
        Course course = courseRepository.findCourseByCourseName(dto.getCourseName());
        Professor professor = course.getProfessor();
        if (!student.getProfessorsOfStudent().contains(professor)) {
            student.getProfessorsOfStudent().add(professor);

        }
        if (!student.getStudentCourses().contains(course)) {
            student.getStudentCourses().add(course);
        } else throw new IllegalStateException("this student already has this course");

    }

}
