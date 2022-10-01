package com.behnam.university.dto.professor;

import com.behnam.university.dto.common.CommonDetailDto;
import com.behnam.university.dto.common.CommonDto;

/**
 * @author Behnam Si (https://github.com/behnamsi/)
 * @version 1.0
 * @since 9/10/2022
 */

public class ProfessorDetailDto extends CommonDetailDto {

    private String firstName;

    private String lastName;

    private String collegeName;

    private Long personalId;

    private Long nationalId;

    public ProfessorDetailDto() {
    }

    public ProfessorDetailDto(
            String firstName, String lastName, String collegeName, Long personalId, Long nationalId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.collegeName = collegeName;
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
        return "ProfessorDetailDto{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", collegeName='" + collegeName + '\'' +
                ", personalId=" + personalId +
                ", nationalId=" + nationalId +
                '}';
    }
}
