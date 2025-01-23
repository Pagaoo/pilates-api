package com.dev.pilates.services.professor;

import com.dev.pilates.dtos.professor.ProfessorRequestDTO;
import com.dev.pilates.dtos.professor.ProfessorResponseDTO;
import com.dev.pilates.entities.Professor;
import com.dev.pilates.entities.Roles;
import com.dev.pilates.exceptions.CreatingEntityException;
import com.dev.pilates.repositories.ProfessorRepository;
import com.dev.pilates.repositories.RolesRepository;
import com.dev.pilates.specifications.ProfessorSpecifications;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProfessorServices {
    private final ProfessorRepository professorRepository;
    private final RolesRepository rolesRepository;

    public ProfessorServices(ProfessorRepository professorRepository, RolesRepository rolesRepository) {
        this.professorRepository = professorRepository;
        this.rolesRepository = rolesRepository;
    }

    public ProfessorRequestDTO save(ProfessorRequestDTO professorDTO) {
        if (professorDTO.password().trim().isEmpty() || professorDTO.password().isBlank()) {
            throw new CreatingEntityException("password cannot be empty");
        }
        Roles role = rolesRepository.findById(professorDTO.role_id()).orElseThrow(() ->
                new EntityNotFoundException(String.format("Role de id: %s não encontrada", professorDTO.role_id())));
        try {
            Professor newProfessor = professorDTO.toProfessor(role);
            professorRepository.save(newProfessor);
            return newProfessor.toProfessorRequestDTO();
        } catch (DataIntegrityViolationException e) {
            throw new CreatingEntityException("Erro ao criar professor");
        }
    }

    public List<ProfessorResponseDTO> findAll() {
        try {
            List<Professor> professorList = professorRepository.findAll();
            return professorList.stream().map(Professor::toProfessorResponseDTO).collect(Collectors.toList());
        } catch (RuntimeException e) {
            throw new EntityNotFoundException("Professores não encontrados");
        }
    }

    public ProfessorResponseDTO findProfessorById(Long id) {
            Professor professor = professorRepository.findById(id).orElseThrow(() ->
                    new EntityNotFoundException("Professor não encontrado"));
            return professor.toProfessorResponseDTO();
    }

    public List<ProfessorResponseDTO> findProfessorsByName(String username) {
        try {
            Specification<Professor> specification = ProfessorSpecifications.usernameContainsIgnoreCase(username);
            return professorRepository.findAll(specification).stream().map(Professor::toProfessorResponseDTO).collect(Collectors.toList());
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException("Professores não encontrados");
        }

    }

    public ProfessorResponseDTO findProfessorByEmail(String email) {
        try {
            Professor professor = professorRepository.findProfessorByEmail(email);
            return professor.toProfessorResponseDTO();
        } catch (RuntimeException e) {
            throw new EntityNotFoundException(String.format("Professor de email: %s não encontrado", email));
        }
    }

    public void deleteProfessor(Long id) {
        try {
            Professor professorToDelete = professorRepository.findProfessorById(id);
            professorRepository.delete(professorToDelete);
        } catch (InvalidDataAccessApiUsageException e) {
            throw new EntityNotFoundException("Professor não encontrado para exclusão");
        }

    }
}
