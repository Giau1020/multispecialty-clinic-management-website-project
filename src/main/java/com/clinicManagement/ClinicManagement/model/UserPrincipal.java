package com.clinicManagement.ClinicManagement.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class UserPrincipal implements UserDetails {
    private static final long serialVersionUID = 1L;


    private User user;

    public UserPrincipal(User user) {
        this.user=user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<String> roles = user.getUserRoles()
                .stream()
                .map(userRole -> "ROLE_" + userRole.getRole().getRoleName().toUpperCase())
                .toList();
        List<SimpleGrantedAuthority> list = roles
                .stream()
                .map(role -> {return new SimpleGrantedAuthority(role);})
                .toList();

        return list;
    }

    @Override
    public String getPassword() {

        return user.getPasswordHash();
    }

    @Override
    public String getUsername() {

        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;	}


    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }


    @Override
    public boolean isEnabled() {
        return true;
    }
}
