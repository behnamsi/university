package com.behnam.university.mapper;


import com.behnam.university.dto.professor.ProfessorCreateDto;
import com.behnam.university.model.Professor;

public class ProfessorMapper {
    public ProfessorCreateDto professorToDto(Professor professor) {
        String firstName = professor.getFirstName();
        String lastName = professor.getLastName();
        long personalId = professor.getPersonalId();
        long nationalId = professor.getNationalId();
        return new ProfessorCreateDto(
                firstName,
                lastName,
                personalId,
                nationalId);
    }

    public Professor dtoTOProfessor(ProfessorCreateDto professorCreateDto) {
        String firstName = professorCreateDto.getFirstName();
        String lastName = professorCreateDto.getLastName();
        long personalId = professorCreateDto.getPersonalId();
        long nationalId = professorCreateDto.getNationalId();
        return new Professor(
                firstName,
                lastName,
                personalId,
                nationalId);
    }
}
