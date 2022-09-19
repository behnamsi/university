package com.behnam.university.repository;

import com.behnam.university.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Behnam Si (https://github.com/behnamsi/)
 * @version 1.0
 * @since 9/18/2022
 */

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findAppUserByUsername(String username);

    @Modifying
    void deleteAppUserByUsername(String username);

    boolean existsAppUserByUsername(String username);
}
