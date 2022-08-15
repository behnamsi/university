package com.behnam.university.dto;


import com.behnam.university.validation.annotations.ValidNationalId;
import com.behnam.university.validation.annotations.ValidSevenDigits;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ProfessorDto {
    @NotEmpty
    @NotNull
    @Size(min = 3, max = 20, message = "first name length must be between 3 and 20")
    private String firstName;
    @NotEmpty
    @NotNull
    @Size(min = 3, max = 20, message = "last name length must be between 3 and 20")
    private String lastName;
    private String collegeName;
    @NotNull
    @ValidSevenDigits
    private Long personalId;
    @NotNull
    @ValidNationalId
    private Long nationalId;

    public ProfessorDto() {
    }

    public ProfessorDto(String firstName, String lastName, String collegeName, Long personalId, Long nationalId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.collegeName = collegeName;
        this.personalId = personalId;
        this.nationalId = nationalId;
    }

    public ProfessorDto(String firstName, String lastName, Long personalId, Long nationalId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.personalId = personalId;
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

    public String getCollegeName() {
        return collegeName;
    }

    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
    }

    public Long getPersonalId() {
        return personalId;
    }

    public void setPersonalId(Long personalId) {
        this.personalId = personalId;
    }

    public Long getNationalId() {
        return nationalId;
    }

    public void setNationalId(Long nationalId) {
        this.nationalId = nationalId;
    }

    @Override
    public String toString() {
        return "ProfessorDto{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", collegeName='" + collegeName + '\'' +
                ", personalId=" + personalId +
                ", nationalId=" + nationalId +
                '}';
    }
}
