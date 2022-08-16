package com.behnam.university.student;


import com.behnam.university.model.College;
import com.behnam.university.model.Student;
import com.behnam.university.repository.CollegeRepository;
import com.behnam.university.repository.StudentRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class StudentRepositoryUnitTest {


    @Autowired
    StudentRepository repository;

    @Autowired
    CollegeRepository collegeRepository;
    Student student;

    @BeforeEach
    void setStudents(){
        student=new Student();
        student.setFirstName("behnam");
        student.setLastName("saghari");
        student.setNationalId(1234567890L);
        student.setUniversityId(1234567L);
    }
    @Test
    void shouldFindStudentByUniId() {
        College college = new College();
        college.setCollegeName("computer");
        collegeRepository.save(college);
        student.setStudentCollege(college);
        repository.save(student);
        System.err.println("repository.findAll() = " + repository.findAll());

        Assertions.assertThat(repository.findAll()).isNotNull();
    }

    @AfterEach
    void tearDown() {
        repository.deleteAll();
        collegeRepository.deleteAll();
    }
}
