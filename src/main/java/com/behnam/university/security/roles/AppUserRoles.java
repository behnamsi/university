package com.behnam.university.security.roles;


import com.google.common.collect.Sets;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static com.behnam.university.security.roles.AppUserPermission.*;

/**
 * @author Behnam Si (https://github.com/behnamsi/)
 * @version 1.0
 * @since 9/5/2022
 */

public enum AppUserRoles {
    STUDENT(Sets.newHashSet(
            COLLEGE_READ,
            COURSE_READ)),

    PROFESSOR(Sets.newHashSet(
            COLLEGE_READ,
            COURSE_READ)),
    MANAGER(Sets.newHashSet(
            COLLEGE_READ,
            COURSE_READ
    )),
    ADMIN(Sets.newHashSet(
            STUDENT_WRITE,
            STUDENT_READ,
            PROFESSOR_WRITE,
            PROFESSOR_READ,
            COURSE_READ,
            COURSE_WRITE,
            COLLEGE_READ,
            COLLEGE_WRITE));

    private final Set<AppUserPermission> permissions;

    AppUserRoles(Set<AppUserPermission> permissions) {
        this.permissions = permissions;
    }

    public Set<AppUserPermission> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getAuthorities() {
        Set<SimpleGrantedAuthority> permissionSet = getPermissions()
                .stream()
                .map(permissions -> new SimpleGrantedAuthority(permissions.getPermission()))
                .collect(Collectors.toSet());
        permissionSet.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return permissionSet;
    }
}
