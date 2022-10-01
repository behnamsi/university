package com.behnam.university.user;

import com.behnam.university.dto.user.AppUserCreateDto;
import com.behnam.university.service.implemention.AppUserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author Behnam Si (https://github.com/behnamsi/)
 * @version 1.0
 * @since 9/21/2022
 */
@SpringBootTest
public class UserTest {
    @Autowired
    AppUserServiceImpl service;

    @Test
    void addUser() {
        AppUserCreateDto user=new AppUserCreateDto("<%$>","12345678","12345678");
        service.addUser(user);
    }
}
