package com.dev.pilates.config.components;

import com.dev.pilates.entities.Professor;
import com.dev.pilates.repositories.ProfessorRepository;
import com.dev.pilates.services.role.RoleService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final ProfessorRepository professorRepository;
    private final RoleService roleService;

    public CustomAuthenticationProvider(ProfessorRepository professorRepository, RoleService roleService) {
        this.professorRepository = professorRepository;
        this.roleService = roleService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        final String username = authentication.getName();
        final String password = authentication.getCredentials().toString();

        Professor professor = professorRepository.findProfessorByUsername(username);

        if (professor == null) {
            throw new BadCredentialsException("Invalid username or password");
        }

        if (!BCrypt.checkpw(password, professor.getPassword())) {
            throw new BadCredentialsException("Senha incorreta");
        }

        String roleName = roleService.getRoleNameById(professor.getRole().getId());

        List<GrantedAuthority> grantedAuthorities = Collections.singletonList(new SimpleGrantedAuthority(roleName));

        return new UsernamePasswordAuthenticationToken(professor.getUsername(), password, grantedAuthorities);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

}
