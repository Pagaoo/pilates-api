package com.dev.pilates.services.professor;

import com.dev.pilates.dtos.professor.ProfessorDTO;
import com.dev.pilates.entities.Professor;
import com.dev.pilates.entities.Roles;
import com.dev.pilates.repositories.ProfessorRepository;
import com.dev.pilates.repositories.RolesRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProfessorServices {
    private final ProfessorRepository professorRepository;
    private final RolesRepository rolesRepository;
    private final PasswordEncoder passwordEncoder;

    public ProfessorServices(ProfessorRepository professorRepository, RolesRepository rolesRepository, PasswordEncoder passwordEncoder) {
        this.professorRepository = professorRepository;
        this.rolesRepository = rolesRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<ProfessorDTO> findAll() {
        List<Professor> professorList = professorRepository.findAll();
        List<ProfessorDTO> professorDTOList = professorList.stream().map(Professor::toProfessorDTO).collect(Collectors.toList());
        return professorDTOList;
    }

    public ProfessorDTO findProfessorById(Long id) {
        Professor professor = professorRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Professor não encontrado"));
        return professor.toProfessorDTO();
    }

    public List<ProfessorDTO> findProfessorsByName(String username) {
        List<ProfessorDTO> professorDTOSList = professorRepository.findProfessorsByUsername(username);

        if (professorDTOSList.isEmpty()) {
            return new ArrayList<>();
        }

        return professorDTOSList;
    }

    public ProfessorDTO save(@Valid ProfessorDTO professorDTO) {
        try {
            Roles role = rolesRepository.findById(professorDTO.role_id()).orElseThrow(() ->
                    new EntityNotFoundException(String.format("Role de id: %s não encontrada", professorDTO.role_id())));
            Professor newProfessor = convertToProfessorDTO(professorDTO, role);
            professorRepository.save(newProfessor);
            return newProfessor.toProfessorDTO();
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Erro de integridade ao criar professor", e.getCause());
        }
    }

    public Professor findProfessorByUsername(String username) {
        try {
            Professor professor = professorRepository.findProfessorByUsername(username);
            return professor;
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException("Professor não encontrado");
        }
    }

    private Professor convertToProfessorDTO(ProfessorDTO professorDTO, Roles roles) {
        Professor professorRequest = new Professor();
        professorRequest.setUsername(professorDTO.username());
        professorRequest.setEmail(professorDTO.email());
        professorRequest.setRole(roles);
        professorRequest.setCreated_at(LocalDateTime.now());
        professorRequest.setUpdated_at(LocalDateTime.now());
        return professorRequest;
    }
}
