package com.dev.pilates.services.professor;

import com.dev.pilates.entities.Professor;
import com.dev.pilates.services.role.RoleService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class CustomProfessorDetailsService implements UserDetailsService {

    private final ProfessorServices professorServices;
    private final RoleService roleService;

    public CustomProfessorDetailsService(ProfessorServices professorServices, RoleService roleService) {
        this.professorServices = professorServices;
        this.roleService = roleService;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Professor professor = professorServices.findProfessorByUsername(username);

        String roleName = roleService.getRoleNameById(professor.getId());

        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(roleName));

        return new org.springframework.security.core.userdetails.User(
                professor.getUsername(), professor.getPassword(), authorities
        );
    }
}
