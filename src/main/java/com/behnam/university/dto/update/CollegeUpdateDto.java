package com.behnam.university.dto.update;

import com.behnam.university.validation.annotations.ValidName;

/**
 * @author Behnam Si (https://github.com/behnamsi/)
 * @version 1.0
 * @since 9/12/2022
 */

public class CollegeUpdateDto {
    @ValidName
    private String collegeName;

    public CollegeUpdateDto() {
    }

    public CollegeUpdateDto(String collegeName) {
        this.collegeName = collegeName;
    }

    public String getCollegeName() {
        return collegeName;
    }

    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
    }

    @Override
    public String toString() {
        return "CollegeUpdateDto{" +
                "collegeName='" + collegeName + '\'' +
                '}';
    }
}
