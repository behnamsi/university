package com.behnam.university.dto;


import com.behnam.university.validation.annotations.ValidNationalId;
import com.behnam.university.validation.annotations.ValidSevenDigits;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class StudentDto {
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
    @ValidNationalId
    private long nationalId;
    @NotNull
    @ValidSevenDigits
    private long universityId;

    public StudentDto() {
    }

    public StudentDto(String firstName, String lastName, long nationalId, long universityId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.nationalId = nationalId;
        this.universityId = universityId;
    }

    public StudentDto(String firstName, String lastName, String collegeName, long nationalId, long universityId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.collegeName = collegeName;
        this.nationalId = nationalId;
        this.universityId = universityId;
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

    public long getNationalId() {
        return nationalId;
    }

    public void setNationalId(long nationalId) {
        this.nationalId = nationalId;
    }

    public long getUniversityId() {
        return universityId;
    }

    public void setUniversityId(long universityId) {
        this.universityId = universityId;
    }

    @Override
    public String toString() {
        return "StudentDto{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", collegeName='" + collegeName + '\'' +
                ", nationalId=" + nationalId +
                ", universityId=" + universityId +
                '}';
    }
}
