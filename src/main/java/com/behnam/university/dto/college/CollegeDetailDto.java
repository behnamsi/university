package com.behnam.university.dto.college;

import java.util.List;

/**
 * @author Behnam Si (https://github.com/behnamsi/)
 * @version 1.0
 * @since 9/10/2022
 */

public class CollegeDetailDto {
    private String collegeName;
    private List<String> professors;
    private List<String> students;
    private List<String> courses;


    public CollegeDetailDto() {
    }

    public CollegeDetailDto(String collegeName, List<String> professors, List<String> students, List<String> courses) {
        this.collegeName = collegeName;
        this.professors = professors;
        this.students = students;
        this.courses = courses;
    }

    public String getCollegeName() {
        return collegeName;
    }

    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
    }

    public List<String> getProfessors() {
        return professors;
    }

    public void setProfessors(List<String> professors) {
        this.professors = professors;
    }

    public List<String> getStudents() {
        return students;
    }

    public void setStudents(List<String> students) {
        this.students = students;
    }

    public List<String> getCourses() {
        return courses;
    }

    public void setCourses(List<String> courses) {
        this.courses = courses;
    }

    @Override
    public String toString() {
        return "CollegeDetailDto{" +
                "collegeName='" + collegeName + '\'' +
                ", professors=" + professors +
                ", students=" + students +
                ", courses=" + courses +
                '}';
    }
}
