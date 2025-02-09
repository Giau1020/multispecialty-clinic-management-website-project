package com.clinicManagement.ClinicManagement.service;

import com.clinicManagement.ClinicManagement.model.User;
import com.clinicManagement.ClinicManagement.model.UserPrincipal;
import com.clinicManagement.ClinicManagement.model.UserRole;
import com.clinicManagement.ClinicManagement.repository.UserRepository;

import com.clinicManagement.ClinicManagement.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MyUserDetailService implements UserDetailsService {
    @Autowired
    private UserRepository repo;

    @Autowired
    private UserRoleRepository userRoleRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user= repo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        List<UserRole> roles = userRoleRepository.findByUserRoleIdUserId(user.getUserId());
        user.setUserRoles(roles);

        if (user==null) {
            System.out.println("User 404");
            throw new UsernameNotFoundException("User 404");
        }
        return new UserPrincipal(user);
    }
}
