package com.behnam.university.mapper;


import com.behnam.university.dto.StudentDto;
import com.behnam.university.model.Student;

public class StudentMapper {
    public StudentDto studentToDto(Student student){
        String firsName=student.getFirstName();
        String lastName=student.getLastName();
        String collegeName=student.getStudentCollege().getCollegeName();
        long nationalId=student.getNationalId();
        long universityId=student.getUniversityId();
        return new StudentDto(
                firsName,
                lastName,
                collegeName,
                nationalId,
                universityId
        );
    }

    public Student dtoTOStudent(StudentDto studentDto){
        String firsName=studentDto.getFirstName();
        String lastName=studentDto.getLastName();
        long nationalId=studentDto.getNationalId();
        long universityId=studentDto.getUniversityId();
        return new Student(
                firsName,
                lastName,
                nationalId,
                universityId);
    }
}
