package com.behnam.university.dto.course;


import com.behnam.university.dto.common.CommonCreateDto;
import com.behnam.university.dto.common.CommonDto;
import com.behnam.university.validation.annotations.ValidName;

import javax.validation.constraints.*;

public class CourseCreateDto extends CommonCreateDto {
    @NotNull
    @ValidName
    private String courseName;
    @NotNull
    @Min(1)
    @Max(3)
    private Integer unitNumber;


    public CourseCreateDto(String courseName, Integer unitNumber) {
        this.courseName = courseName;
        this.unitNumber = unitNumber;
    }

    public CourseCreateDto() {
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

//    public String getProfessorOfCourse() {
//        return professorOfCourse;
//    }
//
//    public void setProfessorOfCourse(String professorOfCourse) {
//        this.professorOfCourse = professorOfCourse;
//    }
//
//    public String getCollegeName() {
//        return collegeName;
//    }
//
//    public void setCollegeName(String collegeName) {
//        this.collegeName = collegeName;
//    }

    @Override
    public String toString() {
        return "CourseDto{" +
                "name='" + courseName + '\'' +
                ", unitNumber=" + unitNumber +
                '}';
    }
}
