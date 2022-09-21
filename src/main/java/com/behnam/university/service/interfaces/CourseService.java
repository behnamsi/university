package com.behnam.university.service.interfaces;

import com.behnam.university.dto.course.CourseCreateDto;
import com.behnam.university.dto.course.CourseDetailDto;
import com.behnam.university.dto.course.CourseListDto;
import com.behnam.university.dto.course.CourseUpdateDto;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Behnam Si (https://github.com/behnamsi/)
 * @version 1.0
 * @since 8/29/2022
 */

@Service
public interface CourseService {

    List<CourseCreateDto> getAllCourses(Integer page, Integer limit);

    List<CourseListDto> getAllCourses(Pageable pageable);

    CourseCreateDto addCourse(CourseCreateDto courseCreateDto, Long professorPersonalId, String collegeName);

    String deleteCourseByName(String courseName);

    void updateCourse(Long courseId, String courseName, Integer unitNumber, Long professorId);
    CourseUpdateDto updateCourse(Long courseId, CourseUpdateDto dto);

    CourseDetailDto getCourse(Long courseId);
}
