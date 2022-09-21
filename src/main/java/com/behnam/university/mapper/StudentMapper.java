package com.behnam.university.mapper;


import com.behnam.university.dto.student.StudentCreateDto;
import com.behnam.university.model.Student;

public class StudentMapper {
    public StudentCreateDto studentToDto(Student student){
        String firsName=student.getFirstName();
        String lastName=student.getLastName();
        String collegeName=student.getStudentCollege().getCollegeName();
        long nationalId=student.getNationalId();
        long universityId=student.getUniversityId();
        return new StudentCreateDto(
                firsName,
                lastName,
                nationalId,
                universityId
        );
    }

    public Student dtoTOStudent(StudentCreateDto studentCreateDto){
        String firsName= studentCreateDto.getFirstName();
        String lastName= studentCreateDto.getLastName();
        long nationalId= studentCreateDto.getNationalId();
        long universityId= studentCreateDto.getUniversityId();
        return new Student(
                firsName,
                lastName,
                nationalId,
                universityId);
    }
}
