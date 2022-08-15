package com.behnam.university.dto;


import javax.validation.constraints.*;

public class CourseDto {
    @NotEmpty
    @NotNull
    @Size(min = 3, max = 20, message = "course name length must be between 3 and 20")
    private String name;
    @NotNull
    @Min(1)
    @Max(3)
    private Integer unitNumber;
    private String professorOfCourse;
    private String collegeName;

    public CourseDto(String name, Integer unitNumber, String professorOfCourse, String collegeName) {
        this.name = name;
        this.unitNumber = unitNumber;
        this.professorOfCourse = professorOfCourse;
        this.collegeName = collegeName;
    }

    public CourseDto(String name, Integer unitNumber) {
        this.name = name;
        this.unitNumber = unitNumber;
    }

    public CourseDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    @Override
    public String toString() {
        return "CourseDto{" +
                "name='" + name + '\'' +
                ", unitNumber=" + unitNumber +
                ", professorOfCourse='" + professorOfCourse + '\'' +
                ", collegeName='" + collegeName + '\'' +
                '}';
    }
}
