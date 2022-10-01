package com.behnam.university.dto.college;

import com.behnam.university.dto.common.CommonDto;
import com.behnam.university.dto.common.CommonUpdateDto;
import com.behnam.university.validation.annotations.ValidName;


/**
 * @author Behnam Si (https://github.com/behnamsi/)
 * @version 1.0
 * @since 9/12/2022
 */

public class CollegeUpdateDto extends CommonUpdateDto {
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
