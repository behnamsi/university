package com.behnam.university.dto.appUser;

/**
 * @author Behnam Si (https://github.com/behnamsi/)
 * @version 1.0
 * @since 9/18/2022
 */

public class AppUserListDto {
    private String username;

    public AppUserListDto() {
    }

    public AppUserListDto(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "AppUserListDto{" +
                "username='" + username + '\'' +
                '}';
    }
}
