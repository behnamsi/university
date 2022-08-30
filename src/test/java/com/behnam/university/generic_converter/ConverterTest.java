package com.behnam.university.generic_converter;

import com.behnam.university.dto.CollegeDto;
import com.behnam.university.dto.CourseDto;
import com.behnam.university.dto.ProfessorDto;
import com.behnam.university.dto.StudentDto;
import com.behnam.university.mapper.generic_converter.Converter;
import com.behnam.university.model.College;
import com.behnam.university.model.Course;
import com.behnam.university.model.Professor;
import com.behnam.university.model.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ConverterTest {

    Converter<Student, StudentDto> converter;
    Student student;
    Student student2;
    StudentDto studentDto;
    StudentDto studentDto2;
    College college;
    College college2;
    CollegeDto collegeDto;
    CollegeDto collegeDto2;
    Professor professor;
    Professor professor2;
    ProfessorDto professorDto;
    ProfessorDto professorDto2;
    Converter<StudentDto, Student> dtoConverter;
    Converter<Professor, ProfessorDto> professorDtoConverter;
    Converter<ProfessorDto, Professor> professorConverter;
    Course course;
    Course course2;
    CourseDto courseDto;
    CourseDto courseDto2;
    Converter<Course,CourseDto> courseDtoConverter;
    Converter<CourseDto,Course> courseConverter;
    Converter<College, CollegeDto> collegeDtoConverter;
    Converter<CollegeDto,College> collegeConverter;

    @BeforeEach
    void setUp() {
        converter = new Converter<>();
        dtoConverter = new Converter<>();
        student = new Student();
        student.setStudentId(1L);
        student.setFirstName("Behnam");
        student.setLastName("Saqari");
        student.setNationalId(123L);
        student.setUniversityId(1234L);
        studentDto = new StudentDto();
        college = new College();
        college.setCollegeName("Computer");
        student.setStudentCollege(college);
        professor = new Professor();
        professor.setProfessorId(1L);
        professor.setFirstName("ali");
        professor.setLastName("qul");
        professor.setNationalId(987L);
        professor.setPersonalId(765L);
        professor.setProfessorCollege(college);
        professorDto = new ProfessorDto();
        professorDtoConverter = new Converter<>();
        course = new Course();
        course.setCourseId(1L);
        course.setCourseName("java");
        course.setUnitNumber(3);
        course.setProfessor(professor);
        course.setCourseCollege(college);
        courseDto=new CourseDto();
        courseDtoConverter=new Converter<>();
        List<Course> courseList=new ArrayList<>();
        courseList.add(course);
        college.setCoursesInCollege(courseList);
        collegeDto=new CollegeDto();
        // ------------------------------
        professorConverter = new Converter<>();
        student2 = new Student();
        professor2 = new Professor();
        professorDto2 = new ProfessorDto(
                "abbas",
                "bu azar",
                "computer",
                1234567L,
                987654L);
        studentDto2 = new StudentDto();
        studentDto2.setFirstName("Behnam");
        studentDto2.setLastName("Saqari");
        studentDto2.setNationalId(544L);
        studentDto2.setUniversityId(54486L);
        studentDto2.setCollegeName("Computer");
        course2=new Course();
        courseDto2=new CourseDto("android",3);
        courseConverter=new Converter<>();
        collegeDtoConverter=new Converter<>();
        college2=new College();
        collegeDto2=new CollegeDto("math");
        collegeConverter=new Converter<>();

    }

    @Test
    void shouldConvertEntityToDto() {
        System.out.println(studentDto);
        converter.convert(student, studentDto);
        System.err.println(studentDto);
        System.out.println("student2 = " + student2);
        dtoConverter.convert(studentDto2, student2);
        System.err.println("student2 = " + student2);
        System.out.println("professorDto = " + professorDto);
        professorDtoConverter.convert(professor, professorDto);
        System.err.println("professorDto = " + professorDto);
        System.out.println("professor2 = " + professor2);
        professorConverter.convert(professorDto2, professor2);
        System.err.println("professor2 = " + professor2);
    }

    @Test
    void shouldConvertCourseToCourseDto(){
        System.out.println("courseDto = " + courseDto);
        courseDtoConverter.convert(course,courseDto);
        System.err.println("courseDto = " + courseDto);
        System.out.println();
        System.out.println("course2 = " + course2);
        courseConverter.convert(courseDto2,course2);
        System.out.println("course2 = " + course2);
    }
    @Test
    void shouldConvertCollegeToDto(){
        System.out.println("collegeDto = " + collegeDto);
        collegeDtoConverter.convert(college,collegeDto);
        System.out.println("collegeDto = " + collegeDto);
        System.out.println();
        System.out.println("college2 = " + college2);
        collegeConverter.convert(collegeDto2,college2);
        System.out.println("college2 = " + college2);
    }
}