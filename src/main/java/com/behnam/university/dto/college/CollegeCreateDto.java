package com.behnam.university.dto.college;



import com.behnam.university.dto.common.CommonCreateDto;
import com.behnam.university.dto.common.CommonDto;
import com.behnam.university.validation.annotations.ValidName;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


public class CollegeCreateDto extends CommonCreateDto {
    @NotNull
    @ValidName
    private String collegeName;

    public CollegeCreateDto() {
    }


    public CollegeCreateDto(String collegeName) {
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
        return "CollegeDto{" +
                "name='" + collegeName + '\'' +
                '}';
    }
}

