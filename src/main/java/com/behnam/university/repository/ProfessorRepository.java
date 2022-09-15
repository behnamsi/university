package com.behnam.university.repository;

import com.behnam.university.model.Professor;
import com.behnam.university.model.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProfessorRepository extends JpaRepository<Professor, Long> {
    //    @Query("select p from Professor p where p.nationalId=?1")
//    Optional<Professor> findProfessorByNationalId(Long nationalId);
//
//    @Query("select p from Professor p where p.personalId=?1")
//    Optional<Professor> findProfessorByPersonalId(Long personalId);
    boolean existsProfessorByNationalId(Long nationalId);

    boolean existsProfessorByPersonalId(Long personalId);

    Professor findProfessorByNationalId(Long nationalId);

    Professor findProfessorByPersonalId(Long personalId);

    List<Professor> findProfessorByFirstName(String firstName);

    @Modifying
    void deleteProfessorByPersonalId(Long personalId);

}
