package com.behnam.university.service.interfaces;

import com.behnam.university.dto.ProfessorDto;

import java.util.List;

/**
 * @author Behnam Si (https://github.com/behnamsi/)
 * @version 1.0
 * @since 8/29/2022
 */

public interface ProfessorService {
    List<ProfessorDto> getAllProfessors(Integer page, Integer limit);
    void addProfessor(ProfessorDto professorDto, Long collegeId);
    void deleteProfessor(Long id);
    void updateProfessor(Long id, String first_name,
                         String last_name, Long nationalId, Long personalId);
    List<String> getProfessorStudents(Long professorId);
    List<String> getProfessorStudentsAverages(Long professorId);
    List<String> getProfessorsCourses(Long professorId);
    List<String> getProfessorStudentsByCourse(Long professorId, String courseName);
    List<String> getProfessorStudentsAverageByCourse(Long professorId, String courseName);
    ProfessorDto getProfessor(Long profId);

}
