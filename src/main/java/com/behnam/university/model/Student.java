package com.behnam.university.model;

import com.behnam.university.validation.annotations.ValidNationalId;
import com.behnam.university.validation.annotations.ValidSevenDigits;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Entity
@Table
public class Student {
    // ----- ID -----
    @Id

    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    @Column(updatable = false)
    private Long studentId;
    // ----- END ID -----

    // ----- first name -----
    @Column(nullable = false, length = 20)
    @NotEmpty
    @NotNull
    @Size(min = 3, max = 20, message = "first name must be between 3 and 20")
    private String firstName;
    // ----- end first name -----

    // ----- last name -----
    @Column(nullable = false, length = 20)
    @NotEmpty
    @NotNull
    @Size(min = 3, max = 20, message = "last name length must be between 3 and 20")
    private String lastName;
    // ----- end first name -----

    // -----national id -----
    @Column(nullable = false, unique = true)
    @NotNull
    @ValidNationalId
    private Long nationalId;
    // ----- end national id -----

    // ----- university id -----
    @Column(nullable = false, unique = true)
    @NotNull
    @ValidSevenDigits
    private Long universityId;
    // ----- end university id -----

    // studentCollege
    @ManyToOne(
            cascade = CascadeType.MERGE,
            fetch = FetchType.LAZY
    )
    @JoinColumn(
            name = "college_id",
            referencedColumnName = "collegeId"
    )
    @JsonIgnore
    private College studentCollege;

    // studentCourses
    @ManyToMany(
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            fetch = FetchType.LAZY
    )
    @JoinTable(
            name = "student_courses",
            joinColumns = @JoinColumn(name = "student_id",
                    referencedColumnName = "studentId"),
            inverseJoinColumns = @JoinColumn(name = "course_id",
                    referencedColumnName = "courseId")
    )
    @JsonIgnore
    private List<Course> studentCourses;

    // professorsOfStudent
    @ManyToMany(
            cascade = CascadeType.MERGE,
            fetch = FetchType.LAZY
    )
    @JoinTable(
            name = "student_professor",
            joinColumns = @JoinColumn(name = "studentId"),
            inverseJoinColumns = @JoinColumn(name = "professorId")
    )
    @JsonIgnore
    private List<Professor> professorsOfStudent;

    @ElementCollection
    private Map<String, Double> scores = new HashMap<String, Double>();

    // constructor
    public Student() {
    }

    public Student(String firstName, String lastName, Long nationalId, Long universityId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.nationalId = nationalId;
        this.universityId = universityId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Map<String, Double> getScores() {
        return scores;
    }

    public void setScores(Map<String, Double> scores) {
        this.scores = scores;
    }

    public Long getStudentId() {
        return studentId;
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

    public Long getNationalId() {
        return nationalId;
    }

    public void setNationalId(Long nationalId) {
        this.nationalId = nationalId;
    }

    public Long getUniversityId() {
        return universityId;
    }

    public void setUniversityId(Long universityId) {
        this.universityId = universityId;
    }

    public College getStudentCollege() {
        return studentCollege;
    }

    public void setStudentCollege(College studentCollege) {
        this.studentCollege = studentCollege;
    }

    public List<Course> getStudentCourses() {
        return studentCourses;
    }

    public void setStudentCourses(List<Course> studentCourses) {
        this.studentCourses = studentCourses;
    }

    public List<Professor> getProfessorsOfStudent() {
        return professorsOfStudent;
    }

    public void setProfessorsOfStudent(List<Professor> professorsOfStudent) {
        this.professorsOfStudent = professorsOfStudent;
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentId=" + studentId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", nationalId=" + nationalId +
                ", universityId=" + universityId +
                '}';
    }
}
