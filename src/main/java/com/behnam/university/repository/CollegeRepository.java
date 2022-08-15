package com.behnam.university.repository;

import com.behnam.university.model.College;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

@Repository
public interface CollegeRepository extends JpaRepository<College, Long> {
    boolean existsCollegeByCollegeName(String collegeName);

    College findCollegeByCollegeName(String collegeName);

    @Modifying
    void deleteCollegeByCollegeName(String collegeName);
}
