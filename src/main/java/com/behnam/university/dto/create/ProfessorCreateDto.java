package com.behnam.university.dto.create;


import com.behnam.university.validation.annotations.ValidNationalId;
import com.behnam.university.validation.annotations.ValidSevenDigits;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ProfessorCreateDto {
    @NotEmpty
    @NotNull
    @Size(min = 3, max = 20, message = "first name length must be between 3 and 20")
    private String firstName;
    @NotEmpty
    @NotNull
    @Size(min = 3, max = 20, message = "last name length must be between 3 and 20")
    private String lastName;
    @NotNull
    @ValidSevenDigits
    private Long personalId;
    @NotNull
    @ValidNationalId
    private Long nationalId;

    public ProfessorCreateDto() {
    }


    public ProfessorCreateDto(String firstName, String lastName, Long personalId, Long nationalId) {
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
                ", personalId=" + personalId +
                ", nationalId=" + nationalId +
                '}';
    }
}
