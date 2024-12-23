package com.dev.pilates.dtos.professor;

import com.dev.pilates.entities.Professor;
import com.dev.pilates.entities.Roles;

import java.time.LocalDateTime;

public record ProfessorRequestDTO(String username, String email, String password, Long role_id) {
    public Professor toProfessor(Roles role) {
        Professor professor = new Professor();
        professor.setUsername(username);
        professor.setEmail(email);
        professor.setPassword(password);
        professor.setRole(role);
        professor.setCreated_at(LocalDateTime.now());
        professor.setUpdated_at(LocalDateTime.now());
        return professor;
    }
}
