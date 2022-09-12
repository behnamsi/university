package com.behnam.university.dto.create;



import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


public class CollegeCreateDto {
    @NotEmpty
    @NotNull
    @Size(min = 3, max = 20, message = "college name length must be between 3 and 20")
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

