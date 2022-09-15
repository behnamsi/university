package com.behnam.university.dto.create;


import com.behnam.university.validation.annotations.ValidName;
import com.behnam.university.validation.annotations.ValidNationalId;
import com.behnam.university.validation.annotations.ValidSevenDigits;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class StudentCreateDto {
    @NotNull
    @ValidName
    private String firstName;
    @NotNull
    @ValidName
    private String lastName;
    @NotNull
    @ValidNationalId
    private long nationalId;
    @NotNull
    @ValidSevenDigits
    private long universityId;

    public StudentCreateDto() {
    }

    public StudentCreateDto(String firstName, String lastName, long nationalId, long universityId) {
        this.firstName = firstName;
        this.lastName = lastName;
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
                ", nationalId=" + nationalId +
                ", universityId=" + universityId +
                '}';
    }
}
