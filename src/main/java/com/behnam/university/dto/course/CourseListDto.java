package com.behnam.university.dto.course;

import com.behnam.university.dto.common.CommonDto;
import com.behnam.university.dto.common.CommonListDto;

/**
 * @author Behnam Si (https://github.com/behnamsi/)
 * @version 1.0
 * @since 9/12/2022
 */

public class CourseListDto extends CommonListDto {
    private String courseName;

    public CourseListDto() {
    }

    public CourseListDto(String courseName) {
        this.courseName = courseName;
    }

    @Override
    public String toString() {
        return "CourseListDto{" +
                "courseName='" + courseName + '\'' +
                '}';
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
}
