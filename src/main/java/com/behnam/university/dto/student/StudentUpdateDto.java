package com.behnam.university.dto.student;

import com.behnam.university.dto.common.CommonDto;
import com.behnam.university.dto.common.CommonUpdateDto;
import com.behnam.university.validation.annotations.ValidName;
import com.behnam.university.validation.annotations.ValidNationalId;

import java.util.List;

/**
 * @author Behnam Si (https://github.com/behnamsi/)
 * @version 1.0
 * @since 9/12/2022
 */

public class StudentUpdateDto extends CommonUpdateDto {
    @ValidName
    private String firstName;
    @ValidName
    private String lastName;
    @ValidNationalId
    private Long nationalId;
    private List<String> courses;

    public StudentUpdateDto() {
    }

    public StudentUpdateDto(String firstName, String lastName, Long nationalId, List<String> courses) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.nationalId = nationalId;
        this.courses = courses;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Long getNationalId() {
        return nationalId;
    }

    public void setNationalId(Long nationalId) {
        this.nationalId = nationalId;
    }

    public List<String> getCourses() {
        return courses;
    }

    public void setCourses(List<String> courses) {
        this.courses = courses;
    }

    @Override
    public String toString() {
        return "StudentUpdateDto{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", nationalId=" + nationalId +
                ", courses=" + courses +
                '}';
    }
}
