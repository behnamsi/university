package com.behnam.university.dto.studentCourses;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author Behnam Si (https://github.com/behnamsi/)
 * @version 1.0
 * @since 9/16/2022
 */

public class StudentCourseScoreDto {
    @NotNull
    private String courseName;
    @NotNull
    @Min(0)
    @Max(20)
    private Double score;

    public StudentCourseScoreDto() {
    }

    public StudentCourseScoreDto(String courseName, Double score) {
        this.courseName = courseName;
        this.score = score;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "StudentCourseScoreDto{" +
                "courseName='" + courseName + '\'' +
                ", score=" + score +
                '}';
    }
}

