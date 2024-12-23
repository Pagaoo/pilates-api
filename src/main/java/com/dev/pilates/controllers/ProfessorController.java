package com.dev.pilates.controllers;

import com.dev.pilates.dtos.professor.ProfessorRequestDTO;
import com.dev.pilates.dtos.professor.ProfessorResponseDTO;
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

    @GetMapping("/{id}")
    public ResponseEntity<ProfessorResponseDTO> getProfessorById(@PathVariable long id) {
        ProfessorResponseDTO professorDTO = professorServices.findProfessorById(id);
        return ResponseEntity.ok(professorDTO);
    }
}
