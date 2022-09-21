package com.behnam.university.generic_converter;

import com.behnam.university.dto.college.CollegeCreateDto;
import com.behnam.university.dto.course.CourseCreateDto;
import com.behnam.university.dto.professor.ProfessorCreateDto;
import com.behnam.university.dto.student.StudentCreateDto;
import com.behnam.university.mapper.generic_converter.Converter;
import com.behnam.university.model.College;
import com.behnam.university.model.Course;
import com.behnam.university.model.Professor;
import com.behnam.university.model.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class ConverterTest {

    Converter<Student, StudentCreateDto> converter;
    Student student;
    Student student2;
    StudentCreateDto studentCreateDto;
    StudentCreateDto studentCreateDto2;
    College college;
    College college2;
    CollegeCreateDto collegeCreateDto;
    CollegeCreateDto collegeCreateDto2;
    Professor professor;
    Professor professor2;
    ProfessorCreateDto professorCreateDto;
    ProfessorCreateDto professorCreateDto2;
    Converter<StudentCreateDto, Student> dtoConverter;
    Converter<Professor, ProfessorCreateDto> professorDtoConverter;
    Converter<ProfessorCreateDto, Professor> professorConverter;
    Course course;
    Course course2;
    CourseCreateDto courseCreateDto;
    CourseCreateDto courseCreateDto2;
    Converter<Course, CourseCreateDto> courseDtoConverter;
    Converter<CourseCreateDto,Course> courseConverter;
    Converter<College, CollegeCreateDto> collegeDtoConverter;
    Converter<CollegeCreateDto,College> collegeConverter;

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
        studentCreateDto = new StudentCreateDto();
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
        professorCreateDto = new ProfessorCreateDto();
        professorDtoConverter = new Converter<>();
        course = new Course();
        course.setCourseId(1L);
        course.setCourseName("java");
        course.setUnitNumber(3);
        course.setProfessor(professor);
        course.setCourseCollege(college);
        courseCreateDto =new CourseCreateDto();
        courseDtoConverter=new Converter<>();
        List<Course> courseList=new ArrayList<>();
        courseList.add(course);
        college.setCoursesInCollege(courseList);
        collegeCreateDto =new CollegeCreateDto();
        // ------------------------------
        professorConverter = new Converter<>();
        student2 = new Student();
        professor2 = new Professor();
        professorCreateDto2 = new ProfessorCreateDto(
                "abbas",
                "bu azar",
                1234567L,
                987654L);
        studentCreateDto2 = new StudentCreateDto();
        studentCreateDto2.setFirstName("Behnam");
        studentCreateDto2.setLastName("Saqari");
        studentCreateDto2.setNationalId(544L);
        studentCreateDto2.setUniversityId(54486L);
        course2=new Course();
        courseCreateDto2 =new CourseCreateDto("android",3);
        courseConverter=new Converter<>();
        collegeDtoConverter=new Converter<>();
        college2=new College();
        collegeCreateDto2 =new CollegeCreateDto("math");
        collegeConverter=new Converter<>();

    }

    @Test
    void shouldConvertEntityToDto() {
        System.out.println(studentCreateDto);
        converter.convert(student, studentCreateDto);
        System.err.println(studentCreateDto);
        System.out.println("student2 = " + student2);
        dtoConverter.convert(studentCreateDto2, student2);
        System.err.println("student2 = " + student2);
        System.out.println("professorDto = " + professorCreateDto);
        professorDtoConverter.convert(professor, professorCreateDto);
        System.err.println("professorDto = " + professorCreateDto);
        System.out.println("professor2 = " + professor2);
        professorConverter.convert(professorCreateDto2, professor2);
        System.err.println("professor2 = " + professor2);
    }

    @Test
    void shouldConvertCourseToCourseDto(){
        System.out.println("courseDto = " + courseCreateDto);
        courseDtoConverter.convert(course, courseCreateDto);
        System.err.println("courseDto = " + courseCreateDto);
        System.out.println();
        System.out.println("course2 = " + course2);
        courseConverter.convert(courseCreateDto2,course2);
        System.out.println("course2 = " + course2);
    }
    @Test
    void shouldConvertCollegeToDto(){
        System.out.println("collegeDto = " + collegeCreateDto);
        collegeDtoConverter.convert(college, collegeCreateDto);
        System.out.println("collegeDto = " + collegeCreateDto);
        System.out.println();
        System.out.println("college2 = " + college2);
        collegeConverter.convert(collegeCreateDto2,college2);
        System.out.println("college2 = " + college2);
    }
}