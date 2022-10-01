package com.behnam.university.dto.student;

import com.behnam.university.dto.common.CommonDto;

import javax.validation.constraints.NotNull;

/**
 * @author Behnam Si (https://github.com/behnamsi/)
 * @version 1.0
 * @since 9/16/2022
 */

public class StudentAddCourseDto extends CommonDto {
    @NotNull
    private String courseName;

    public StudentAddCourseDto() {
    }

    public StudentAddCourseDto(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    @Override
    public String toString() {
        return "StudentAddCourseDto{" +
                "courseName='" + courseName + '\'' +
                '}';
    }
}
