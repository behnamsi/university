package com.behnam.university.service.interfaces;

import com.behnam.university.dto.student.StudentCreateDto;
import com.behnam.university.dto.student.StudentDetailDto;
import com.behnam.university.dto.student.StudentListDto;
import com.behnam.university.dto.student.StudentAddCourseDto;
import com.behnam.university.dto.student.StudentCourseScoreDto;
import com.behnam.university.dto.student.StudentUpdateDto;
import com.behnam.university.model.Student;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Behnam Si (https://github.com/behnamsi/)
 * @version 1.0
 * @since 8/29/2022
 */

@Service

public interface StudentService {
    List<StudentCreateDto> getAllStudents(Integer limit, Integer page);

    List<StudentListDto> getAllStudents(Pageable pageable);


    StudentDetailDto getStudent(Long studentUniId);

    StudentCreateDto addStudent(StudentCreateDto studentCreateDto, String collegeName);

    Long deleteStudentByUniId(Long uniId);

    Student updateStudent(
            Long uniId,
            String first_name,
            String last_name,
            List<String> courses,
            Long nationalId);
    StudentUpdateDto updateStudent(Long uniId, StudentUpdateDto dto);
    List<String> getStudentCourses(Long uniId);

    void addScoreCourse(
            Long uniId,
            String courseName,
            Double score);

    void addScoreCourse(
            Long uniId,
            StudentCourseScoreDto dto);

    void deleteStudentCourse(Long uniId, String courseName);

    Double getStudentAverage(Long uniID);

    void addCourseToEnrolledCourse(Long uniId, StudentAddCourseDto dto);
}

