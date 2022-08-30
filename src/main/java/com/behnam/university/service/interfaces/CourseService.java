package com.behnam.university.service.interfaces;

import com.behnam.university.dto.CourseDto;

import java.util.List;

/**
 * @author Behnam Si (https://github.com/behnamsi/)
 * @version 1.0
 * @since 8/29/2022
 */

public interface CourseService {

    List<CourseDto> getAllCourses(Integer page, Integer limit);

    void addCourse(CourseDto courseDto, Long professorPersonalId, String collegeName);

    void deleteCourseByName(String courseName);

    void updateCourse(Long courseId, String courseName, Integer unitNumber, Long professorId);

    CourseDto getCourse(Long courseId);
}
