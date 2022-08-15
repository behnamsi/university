package com.behnam.university.dto;



import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;


public class CollegeDto {
    @NotEmpty
    @NotNull
    @Size(min = 3, max = 20, message = "college name length must be between 3 and 20")
    private String name;
    private List<String> courses;

    public CollegeDto() {
    }

    public CollegeDto(String name, List<String> courses) {
        this.name = name;
        this.courses = courses;
    }

    public CollegeDto(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getCourses() {
        return courses;
    }

    public void setCourses(List<String> courses) {
        this.courses = courses;
    }

    @Override
    public String toString() {
        return "CollegeDto{" +
                "name='" + name + '\'' +
                ", courses=" + courses +
                '}';
    }
}

