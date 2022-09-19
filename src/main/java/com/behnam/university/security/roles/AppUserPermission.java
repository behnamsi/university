package com.behnam.university.security.roles;

/**
 * @author Behnam Si (https://github.com/behnamsi/)
 * @version 1.0
 * @since 9/5/2022
 */

public enum AppUserPermission {
    STUDENT_READ("student:read"),
    STUDENT_WRITE("student:write"),
    PROFESSOR_READ("professor:read"),
    PROFESSOR_WRITE("professor:write"),
    COURSE_READ("course:read"),
    COURSE_WRITE("course:write"),
    COLLEGE_READ("college:read"),
    COLLEGE_WRITE("college:write");
    private final String permission;

    AppUserPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
