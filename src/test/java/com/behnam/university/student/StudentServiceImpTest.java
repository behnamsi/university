package com.behnam.university.student;


import com.behnam.university.dto.StudentDto;
import com.behnam.university.service.implemention.StudentServiceImp;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Validated
class StudentServiceImpTest {

    @Autowired
    private StudentServiceImp service;

    @Test
    void getAllStudents() {
        List<StudentDto> studentDTOS = service.getAllStudents(15, 1);
        System.out.println("studentDTOS = " + studentDTOS);
    }

    @Test
    void getStudentCourses() {
        List<String> courseList = service.getStudentCourses(22L);
        System.out.println("courseList = " + courseList);
    }

    @Test
    void getStudentAverage() {
        Double average = service.getStudentAverage(22L);
        System.out.println("average = " + average);
    }

    @Test
    void addStudent() {
        StudentDto studentDTO = new StudentDto();
        studentDTO.setFirstName("abbas23");
        studentDTO.setLastName("bu azar23");
        studentDTO.setUniversityId(2213438L);
        studentDTO.setNationalId(217111899L);
        service.addStudent(studentDTO, "computer");
    }


    @Test
    void deleteStudentByUniId() {
        service.deleteStudentByUniId(1213434431239L);
    }

    @Test
    void updateStudent() {
        String firstName = null;
        String lastName = null;
        List<String> courses = new ArrayList<>();
        courses.add("algorithem");
        courses.add("java");
        Long nationalId = null;
        service.updateStudent(22L, firstName, lastName, courses, nationalId);
    }

    @Test
    void addScoreCourse() {
        service.addScoreCourse(22L, "java", 20D);
        service.addScoreCourse(22L, "algorithem", 20D);
    }

    @Test
    void deleteStudentCourse(){
        service.deleteStudentCourse(9261156L,"algorithem");
    }


}