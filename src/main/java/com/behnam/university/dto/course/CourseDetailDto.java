package com.behnam.university.dto.course;

import com.behnam.university.dto.common.CommonDetailDto;
import com.behnam.university.dto.common.CommonDto;

/**
 * @author Behnam Si (https://github.com/behnamsi/)
 * @version 1.0
 * @since 9/10/2022
 */

public class CourseDetailDto extends CommonDetailDto {

    private String courseName;
    private Integer unitNumber;
    private String professorOfCourse;
    private String collegeName;

    public CourseDetailDto() {
    }

    public CourseDetailDto(String courseName, Integer unitNumber, String professorOfCourse, String collegeName) {
        this.courseName = courseName;
        this.unitNumber = unitNumber;
        this.professorOfCourse = professorOfCourse;
        this.collegeName = collegeName;
    }

    @Override
    public String toString() {
        return "CourseDetailDto{" +
                "name='" + courseName + '\'' +
                ", unitNumber=" + unitNumber +
                ", professorOfCourse='" + professorOfCourse + '\'' +
                ", collegeName='" + collegeName + '\'' +
                '}';
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Integer getUnitNumber() {
        return unitNumber;
    }

    public void setUnitNumber(Integer unitNumber) {
        this.unitNumber = unitNumber;
    }

    public String getProfessorOfCourse() {
        return professorOfCourse;
    }

    public void setProfessorOfCourse(String professorOfCourse) {
        this.professorOfCourse = professorOfCourse;
    }

    public String getCollegeName() {
        return collegeName;
    }

    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
    }
}
