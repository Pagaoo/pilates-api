package com.dev.pilates.controllers;

import com.dev.pilates.dtos.classes.ClassesRequestDTO;
import com.dev.pilates.dtos.classes.ClassesResponseDTO;
import com.dev.pilates.dtos.classes.utils.ClassesAddOrRemoveStudentDTO;
import com.dev.pilates.entities.Classes;
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

    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN', 'ROLE_PROFESSOR')")
    @DeleteMapping("/{classId}/remove-students")
    public ResponseEntity<ClassesResponseDTO> removeStudentFromClasses(
            @PathVariable long classId,
            @RequestHeader("Professor-Id") long professorId,
            @RequestBody ClassesAddOrRemoveStudentDTO removeStudentResponseDTO) {
        ClassesResponseDTO updatedClass = classesServices.removeStudentFromClasses(classId, professorId, removeStudentResponseDTO);
        return ResponseEntity.status(HttpStatus.OK).body(updatedClass);
    }

    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN', 'ROLE_PROFESSOR')")
    @PostMapping("{classId}/add-students")
    public ResponseEntity<ClassesResponseDTO> addStudentToClass(@PathVariable long classId,
                                                               @RequestHeader("Professor-id") long professorId,
                                                               @RequestBody ClassesAddOrRemoveStudentDTO addStudentsRequestDTO) {
        ClassesResponseDTO updatedStudentList = classesServices.addStudentToClasses(classId, professorId, addStudentsRequestDTO);
        return ResponseEntity.status(HttpStatus.OK).body(updatedStudentList);
    }

    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN', 'ROLE_PROFESSOR')")
    @GetMapping("/{id}")
    public ResponseEntity<ClassesResponseDTO> getClassById(@PathVariable long id) {
        ClassesResponseDTO classes = classesServices.findClassesById(id);
        return ResponseEntity.status(HttpStatus.OK).body(classes);
    }

    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN', 'ROLE_PROFESSOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClass(@PathVariable long id) {
        classesServices.deleteClass(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
