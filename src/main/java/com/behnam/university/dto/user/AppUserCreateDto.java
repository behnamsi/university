package com.behnam.university.dto.user;

import com.behnam.university.dto.common.CommonDto;
import com.behnam.university.validation.annotations.ValidName;

/**
 * @author Behnam Si (https://github.com/behnamsi/)
 * @version 1.0
 * @since 9/18/2022
 */

public class AppUserCreateDto extends CommonDto {
    @ValidName
    private String username;
    private short role;
    private String password;
    private String confirmPassword;

    public AppUserCreateDto() {
    }

    public AppUserCreateDto(String username, String password, String confirmPassword) {
        this.username = username;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

    public short getRole() {
        return role;
    }

    public void setRole(short role) {
        this.role = role;
    }

    public AppUserCreateDto(String username, short role, String password, String confirmPassword) {
        this.username = username;
        this.role = role;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    @Override
    public String toString() {
        return "AppUserCreateDto{" +
                "username='" + username + '\'' +
                ", role=" + role +
                ", password='" + password + '\'' +
                ", confirmPassword='" + confirmPassword + '\'' +
                '}';
    }
}
