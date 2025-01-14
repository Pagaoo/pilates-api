package com.dev.pilates.controllers;

import com.dev.pilates.dtos.professor.ProfessorRequestDTO;
import com.dev.pilates.dtos.professor.ProfessorResponseDTO;
import com.dev.pilates.entities.Professor;
import com.dev.pilates.services.professor.ProfessorServices;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/professor")
public class ProfessorController {

    private final ProfessorServices professorServices;

    public ProfessorController(ProfessorServices professorServices) {
        this.professorServices = professorServices;
    }

    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<ProfessorRequestDTO> createProfessor(@RequestBody @Valid ProfessorRequestDTO professorDTO) {
        ProfessorRequestDTO newProfessor = professorServices.save(professorDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(newProfessor);
    }

    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN', 'ROLE_PROFESSOR')")
    @GetMapping
    public ResponseEntity<List<ProfessorResponseDTO>> getAllProfessors() {
        List<ProfessorResponseDTO> professorDTOList = professorServices.findAll();
        return ResponseEntity.ok(professorDTOList);
    }

    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN', 'ROLE_PROFESSOR')")
    @GetMapping("/{id}")
    public ResponseEntity<ProfessorResponseDTO> getProfessorById(@PathVariable long id) {
        ProfessorResponseDTO professorDTO = professorServices.findProfessorById(id);
        return ResponseEntity.ok(professorDTO);
    }

    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN', 'ROLE_PROFESSOR')")
    @GetMapping("email/{email}")
    public ResponseEntity<ProfessorResponseDTO> getProfessorByEmail(@PathVariable String email) {
        ProfessorResponseDTO professorResponseDTO = professorServices.findProfessorByEmail(email);
        return ResponseEntity.ok(professorResponseDTO);
    }

    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN', 'ROLE_PROFESSOR')")
    @GetMapping("username/{username}")
    public ResponseEntity<List<ProfessorResponseDTO>> getProfessorByUsername(@PathVariable String username) {
        List<ProfessorResponseDTO> professorResponseDTO = professorServices.findProfessorsByName(username);
        return ResponseEntity.ok(professorResponseDTO);
    }

    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProfessorById(@PathVariable long id) {
        professorServices.deleteProfessor(id);
        return ResponseEntity.noContent().build();
    }
}
