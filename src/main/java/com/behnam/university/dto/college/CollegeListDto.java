package com.behnam.university.dto.college;

import com.behnam.university.dto.common.CommonDto;
import com.behnam.university.dto.common.CommonListDto;

/**
 * @author Behnam Si (https://github.com/behnamsi/)
 * @version 1.0
 * @since 9/12/2022
 */

public class CollegeListDto extends CommonListDto {
    private String collegeName;


    public CollegeListDto() {
    }

    public CollegeListDto(String collegeName) {
        this.collegeName = collegeName;
    }

    @Override
    public String toString() {
        return "CollegeListDto{" +
                "collegeName='" + collegeName + '\'' +
                '}';
    }

    public String getCollegeName() {
        return collegeName;
    }

    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
    }
}
