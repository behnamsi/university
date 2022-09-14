package com.behnam.university.service.interfaces;

import com.behnam.university.dto.create.ProfessorCreateDto;
import com.behnam.university.dto.detail.ProfessorDetailDto;
import com.behnam.university.dto.list.ProfessorListDto;
import com.behnam.university.dto.update.ProfessorUpdateDto;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Behnam Si (https://github.com/behnamsi/)
 * @version 1.0
 * @since 8/29/2022
 */

@Service
public interface ProfessorService {
    List<ProfessorCreateDto> getAllProfessors(Integer page, Integer limit);

    List<ProfessorListDto> getAllProfessors(Pageable pageable);

    ProfessorCreateDto addProfessor(ProfessorCreateDto professorCreateDto, Long collegeId);

    Long deleteProfessor(Long id);

    void updateProfessor(Long id, String first_name,
                         String last_name, Long nationalId, Long personalId);

    ProfessorUpdateDto updateProfessor(Long profId, ProfessorUpdateDto dto);
    List<String> getProfessorStudents(Long professorId);

    List<String> getProfessorStudentsAverages(Long professorId);

    List<String> getProfessorsCourses(Long professorId);

    List<String> getProfessorStudentsByCourse(Long professorId, String courseName);

    List<String> getProfessorStudentsAverageByCourse(Long professorId, String courseName);

    ProfessorDetailDto getProfessor(Long profId);

}
