package com.behnam.university.mapper.static_mapper;

import com.behnam.university.dto.create.ProfessorCreateDto;
import com.behnam.university.dto.create.StudentCreateDto;
import com.behnam.university.dto.detail.ProfessorDetailDto;
import com.behnam.university.dto.detail.StudentDetailDto;
import com.behnam.university.model.College;
import com.behnam.university.model.Professor;
import com.behnam.university.model.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;

import static com.behnam.university.mapper.static_mapper.StaticMapper.mapper;

class StaticMapperTest {
    Student student;
    StudentCreateDto studentCreateDto;
    College college;
    Professor professor;

    @BeforeEach
    void setUp() {
        student = new Student();
        student.setStudentId(1L);
        student.setUniversityId(2L);
        student.setNationalId(3L);
        student.setFirstName("Behnam");
        student.setLastName("si");
        studentCreateDto = new StudentCreateDto("Ben", "Si", 1L, 2L);
        college = new College();
        college.setCollegeName("Computer");
        student.setStudentCollege(college);
        professor = new Professor("Qul", "efaf", 4L, 5L);
        professor.setProfessorCollege(college);
    }

    @Test
    void testingMyStaticMapperMethod() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {

        StudentCreateDto studentCreateDto = new StudentCreateDto();
        System.out.println("student = " + student);
        System.out.println("studentDto = " + studentCreateDto);
        mapper(student, studentCreateDto);
        System.out.println("student = " + student);
        System.out.println("studentDto = " + studentCreateDto);
    }

    @Test
    void shouldConvertCreateDtoToEntity() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        Student student = new Student();

        System.out.println("student = " + student);
        System.out.println("studentCreateDto = " + studentCreateDto);
        mapper(studentCreateDto, student);
        System.out.println("student = " + student);
        System.out.println("studentCreateDto = " + studentCreateDto);
    }

    @Test
    void shouldWorkOnProfessor() {
        Professor professor = new Professor();
        ProfessorCreateDto professorCreateDto = new ProfessorCreateDto(
                "Ben",
                "Si", 1L, 2L);
        System.out.println("professor = " + professor);
        System.out.println("professorCreateDto = " + professorCreateDto);
        try {
            mapper(professorCreateDto, professor);
        } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
            e.printStackTrace();
        }

        System.out.println("professor = " + professor);
        System.out.println("professorCreateDto = " + professorCreateDto);
    }

    @Test
    void testStudentListDtoMapper() {
        StudentDetailDto studentDetailDto = new StudentDetailDto();
        System.out.println("student = " + student);
        System.out.println("studentListDto = " + studentDetailDto);
//        StaticMapper.studentEntityToListDto(student, studentListDto);
        System.out.println("student = " + student);
        System.out.println("studentListDto = " + studentDetailDto);
    }

    @Test
    void testProfessorListDto() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        ProfessorDetailDto dto = new ProfessorDetailDto();
        System.out.println("dto = " + dto);
        System.out.println("professor = " + professor);
        mapper(professor,dto);
        System.out.println("dto = " + dto);
        System.out.println("professor = " + professor);

    }

}