package com.behnam.university.dto.professor;

import com.behnam.university.validation.annotations.ValidName;
import com.behnam.university.validation.annotations.ValidNationalId;

/**
 * @author Behnam Si (https://github.com/behnamsi/)
 * @version 1.0
 * @since 9/12/2022
 */

public class ProfessorUpdateDto {
    @ValidName
    private String firstName;
    @ValidName
    private String lastName;
    @ValidNationalId
    private Long nationalId;

    public ProfessorUpdateDto() {
    }

    public ProfessorUpdateDto(String firstName, String lastName, Long nationalId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.nationalId = nationalId;
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

    @Override
    public String toString() {
        return "ProfessorUpdateDto{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", nationalId=" + nationalId +
                '}';
    }
}
