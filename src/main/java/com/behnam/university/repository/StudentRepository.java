package com.behnam.university.repository;


import com.behnam.university.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;


@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
//    @Query("select s from Student  s where s.universityId=?1")
//    Optional<Student> findStudentByUniversityIdOptional(Long universityId);
//
//    @Query("select s from Student  s where s.nationalId=?1")
//    Optional<Student> findStudentByNationalId(Long nationalId);
    Student findStudentByUniversityId(Long uniId);

    Student findStudentByNationalId(Long nationalID);

    @Modifying
    void deleteStudentByUniversityId(Long universityId);


    boolean existsStudentByNationalId(Long nationalId);

    boolean existsStudentByUniversityId(Long uniId);

}

