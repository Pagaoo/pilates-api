package com.dev.pilates.services;

import com.dev.pilates.repositories.ProfessorRepository;
import com.dev.pilates.repositories.RolesRepository;
import org.springframework.stereotype.Service;

@Service
public class ProfessorServices {
    private final ProfessorRepository professorRepository;
    private final RolesRepository rolesRepository;

    public ProfessorServices(ProfessorRepository professorRepository, RolesRepository rolesRepository) {
        this.professorRepository = professorRepository;
        this.rolesRepository = rolesRepository;
    }
}
