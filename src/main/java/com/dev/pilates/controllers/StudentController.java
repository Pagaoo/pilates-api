package com.dev.pilates.controllers;

import com.dev.pilates.dtos.student.StudentRequestDTO;
import com.dev.pilates.dtos.student.StudentResponseDTO;
import com.dev.pilates.services.student.StudentServices;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {

    private final StudentServices studentServices;

    public StudentController(StudentServices studentServices) {
        this.studentServices = studentServices;
    }

    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN', 'ROLE_PROFESSOR')")
    @PostMapping
    public ResponseEntity<StudentRequestDTO> createStudent(@RequestBody @Valid StudentRequestDTO studentRequestDTO) {
        StudentRequestDTO savedStudent = studentServices.save(studentRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedStudent);
    }

    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN', 'ROLE_PROFESSOR')")
    @GetMapping
    public ResponseEntity<List<StudentResponseDTO>> getAllStudents() {
        List<StudentResponseDTO> students = studentServices.findAll();
        return ResponseEntity.ok(students);
    }

    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN', 'ROLE_PROFESSOR')")
    @GetMapping("/{id}")
    public ResponseEntity<StudentResponseDTO> getStudentById(@PathVariable long id) {
        StudentResponseDTO student =  studentServices.findStudentById(id);
        return ResponseEntity.ok(student);
    }

    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN', 'ROLE_PROFESSOR')")
    @GetMapping("name/{firstName}")
    public ResponseEntity<List<StudentResponseDTO>> getStudentsByFirstName(@PathVariable String firstName) {
        List<StudentResponseDTO> studentResponseDTOList = studentServices.findStudentsByName(firstName);
        return ResponseEntity.ok(studentResponseDTOList);
    }

    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN', 'ROLE_PROFESSOR')")
    @PatchMapping("/{id}")
    public ResponseEntity<StudentRequestDTO> updateStudent(@PathVariable Long id, @Valid @RequestBody StudentRequestDTO student) {
        StudentRequestDTO studentToBeUpdated = studentServices.updateStudentById(id, student);
        return ResponseEntity.ok(studentToBeUpdated);
    }

    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN', 'ROLE_PROFESSOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudentById(@PathVariable long id) {
        studentServices.deleteStudentById(id);
        return ResponseEntity.noContent().build();
    }

}
