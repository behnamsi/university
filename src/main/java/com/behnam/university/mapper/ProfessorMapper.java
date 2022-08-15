package com.behnam.university.mapper;


import com.behnam.university.dto.ProfessorDto;
import com.behnam.university.model.Professor;

public class ProfessorMapper {
    public ProfessorDto professorToDto(Professor professor) {
        String firstName = professor.getFirstName();
        String lastName = professor.getLastName();
        String collegeName = professor.getProfessorCollege().getCollegeName();
        long personalId = professor.getPersonalId();
        long nationalId = professor.getNationalId();
        return new ProfessorDto(
                firstName,
                lastName,
                collegeName,
                personalId,
                nationalId);
    }

    public Professor dtoTOProfessor(ProfessorDto professorDto) {
        String firstName = professorDto.getFirstName();
        String lastName = professorDto.getLastName();
        long personalId = professorDto.getPersonalId();
        long nationalId = professorDto.getNationalId();
        return new Professor(
                firstName,
                lastName,
                personalId,
                nationalId);
    }
}
