package com.behnam.university.dto.student;

import com.behnam.university.dto.common.CommonDetailDto;
import com.behnam.university.dto.common.CommonDto;

/**
 * @author Behnam Si (https://github.com/behnamsi/)
 * @version 1.0
 * @since 9/10/2022
 */

public class StudentDetailDto extends CommonDetailDto {

    private String firstName;
    private String lastName;
    private String collegeName;
    private long nationalId;
    private long universityId;

    public StudentDetailDto() {
    }

    public StudentDetailDto(String firstName, String lastName, String collegeName, long nationalId, long universityId) {
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
        return "StudentDetailDto{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", collegeName='" + collegeName + '\'' +
                ", nationalId=" + nationalId +
                ", universityId=" + universityId +
                '}';
    }
}
