package com.behnam.university.dto.course;

import com.behnam.university.dto.common.CommonDto;
import com.behnam.university.dto.common.CommonUpdateDto;
import com.behnam.university.validation.annotations.ValidName;
import com.behnam.university.validation.annotations.ValidSevenDigits;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

/**
 * @author Behnam Si (https://github.com/behnamsi/)
 * @version 1.0
 * @since 9/12/2022
 */

public class CourseUpdateDto extends CommonUpdateDto {
    @ValidName
    private String courseName;
    @Min(1) @Max(3)
    private Integer unitNumber;
    @ValidSevenDigits
    private Long professorPersonalId;

    public CourseUpdateDto() {
    }

    public CourseUpdateDto(String courseName, Integer unitNumber, Long professorPersonalId) {
        this.courseName = courseName;
        this.unitNumber = unitNumber;
        this.professorPersonalId = professorPersonalId;
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

    public Long getProfessorPersonalId() {
        return professorPersonalId;
    }

    public void setProfessorPersonalId(Long professorPersonalId) {
        this.professorPersonalId = professorPersonalId;
    }

    @Override
    public String toString() {
        return "CourseUpdateDto{" +
                "CourseName='" + courseName + '\'' +
                ", unitNumber=" + unitNumber +
                ", professorPersonalId=" + professorPersonalId +
                '}';
    }
}
