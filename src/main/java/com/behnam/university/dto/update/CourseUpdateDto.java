package com.behnam.university.dto.update;

import com.behnam.university.validation.annotations.ValidName;
import com.behnam.university.validation.annotations.ValidSevenDigits;

import javax.validation.constraints.Size;

/**
 * @author Behnam Si (https://github.com/behnamsi/)
 * @version 1.0
 * @since 9/12/2022
 */

public class CourseUpdateDto {
    @ValidName
    private String CourseName;
    @Size(min = 1, max = 3)
    private Integer unitNumber;
    @ValidSevenDigits
    private Long professorPersonalId;

    public CourseUpdateDto() {
    }

    public CourseUpdateDto(String courseName, Integer unitNumber, Long professorPersonalId) {
        CourseName = courseName;
        this.unitNumber = unitNumber;
        this.professorPersonalId = professorPersonalId;
    }

    public String getCourseName() {
        return CourseName;
    }

    public void setCourseName(String courseName) {
        CourseName = courseName;
    }

    public Integer getUnitNumber() {
        return unitNumber;
    }

    public void setUnitNumber(Integer unitNumber) {
        this.unitNumber = unitNumber;
    }

    public Long getProfessorPersonalId() {
        return professorPersonalId;
    }

    public void setProfessorPersonalId(Long professorPersonalId) {
        this.professorPersonalId = professorPersonalId;
    }

    @Override
    public String toString() {
        return "CourseUpdateDto{" +
                "CourseName='" + CourseName + '\'' +
                ", unitNumber=" + unitNumber +
                ", professorPersonalId=" + professorPersonalId +
                '}';
    }
}
