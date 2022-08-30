package com.behnam.university.service.interfaces;

import com.behnam.university.dto.StudentDto;
import com.behnam.university.model.Student;

import java.util.List;

/**
 * @author Behnam Si (https://github.com/behnamsi/)
 * @version 1.0
 * @since 8/29/2022
 */

public interface StudentService {
    List<StudentDto> getAllStudents(Integer limit, Integer page);

    StudentDto getStudent(Long studentUniId);

    Student addStudent(StudentDto studentDto, String collegeName);

    void deleteStudentByUniId(Long uniId);

    Student updateStudent(
            Long uniId,
            String first_name,
            String last_name,
            List<String> courses,
            Long nationalId);

    List<String> getStudentCourses(Long uniId);

    void addScoreCourse(
            Long uniId,
            String courseName,
            Double score);

    void deleteStudentCourse(Long uniId, String courseName);

    Double getStudentAverage(Long uniID);
}

