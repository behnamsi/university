package com.behnam.university.model;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * @author Behnam Si (https://github.com/behnamsi/)
 * @version 1.0
 * @since 9/18/2022
 */

@Entity
@Table(name = "users")
public class AppUser implements UserDetails {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long userID;
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<SimpleGrantedAuthority> authorities;
    @Column(unique = true)
    private String username;
    private String password;
    private boolean isAccountNonExpired;
    private boolean isAccountNonLocked;
    private boolean isCredentialsNonExpired;
    private boolean isEnabled;

    public AppUser() {
    }

    public AppUser(Set<SimpleGrantedAuthority> authorities, String username, String password, boolean isAccountNonExpired, boolean isAccountNonLocked, boolean isCredentialsNonExpired, boolean isEnabled) {
        this.authorities = authorities;
        this.username = username;
        this.password = password;
        this.isAccountNonExpired = isAccountNonExpired;
        this.isAccountNonLocked = isAccountNonLocked;
        this.isCredentialsNonExpired = isCredentialsNonExpired;
        this.isEnabled = isEnabled;
    }

    @Override
    public Set<SimpleGrantedAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<SimpleGrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isAccountNonExpired;
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        isAccountNonExpired = accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        isAccountNonLocked = accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isCredentialsNonExpired;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        isCredentialsNonExpired = credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }
}
