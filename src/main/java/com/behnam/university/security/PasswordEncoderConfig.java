package com.behnam.university.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author Behnam Si (https://github.com/behnamsi/)
 * @version 1.0
 * @since 9/18/2022
 */

@Configuration
public class PasswordEncoderConfig {
    @Bean
    @Primary
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
