package com.dev.pilates.controllers;

import com.dev.pilates.dtos.classes.ClassesRequestDTO;
import com.dev.pilates.dtos.classes.ClassesResponseDTO;
import com.dev.pilates.services.classes.ClassesServices;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/classes")
public class ClassesController {

    private final ClassesServices classesServices;

    public ClassesController(ClassesServices classesServices) {
        this.classesServices = classesServices;
    }

    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN', 'ROLE_PROFESSOR')")
    @PostMapping
    public ResponseEntity<ClassesRequestDTO> addClass(@RequestBody @Valid ClassesRequestDTO classesRequestDTO) {
        ClassesRequestDTO newClass = classesServices.save(classesRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(newClass);
    }

    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN', 'ROLE_PROFESSOR')")
    @GetMapping
        public ResponseEntity<List<ClassesResponseDTO>> getAllClasses() {
            List<ClassesResponseDTO> classesList = classesServices.findAll();
            return ResponseEntity.status(HttpStatus.OK).body(classesList);
    }
}
