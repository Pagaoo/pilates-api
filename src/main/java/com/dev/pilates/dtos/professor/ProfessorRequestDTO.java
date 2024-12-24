package com.dev.pilates.dtos.professor;

import com.dev.pilates.entities.Professor;
import com.dev.pilates.entities.Roles;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;

public record ProfessorRequestDTO(String username, String email, String password, Long role_id) {
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public Professor toProfessor(Roles role) {
        String hashedPassword = encoder.encode(password);
        Professor professor = new Professor();
        professor.setUsername(username);
        professor.setEmail(email);
        professor.setPassword(hashedPassword);
        professor.setRole(role);
        professor.setCreated_at(LocalDateTime.now());
        professor.setUpdated_at(LocalDateTime.now());
        return professor;
    }
}
