package com.dev.pilates.controllers;

import com.dev.pilates.dtos.classes.ClassesRequestDTO;
import com.dev.pilates.services.classes.ClassesServices;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
