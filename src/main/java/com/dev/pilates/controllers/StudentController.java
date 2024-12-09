package com.dev.pilates.controllers;

import com.dev.pilates.dtos.student.StudentRequestDTO;
import com.dev.pilates.dtos.student.StudentResponseDTO;
import com.dev.pilates.entities.Student;
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

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_PROFESSOR')")
    @PostMapping
    public ResponseEntity<StudentRequestDTO> createStudent(@RequestBody @Valid StudentRequestDTO studentRequestDTO) {
        StudentRequestDTO savedStudent = studentServices.save(studentRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedStudent);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_PROFESSOR')")
    @GetMapping
    public ResponseEntity<List<StudentResponseDTO>> getAllStudents() {
        List<StudentResponseDTO> students = studentServices.findAll();
        return ResponseEntity.ok(students);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentResponseDTO> getStudentById(@PathVariable long id) {
        StudentResponseDTO student =  studentServices.findById(id);
        return ResponseEntity.ok(student);
    }

    @GetMapping("name/{firstName}")
    public ResponseEntity<List<StudentResponseDTO>> getStudentsByFirstName(@PathVariable String firstName) {
        List<StudentResponseDTO> studentResponseDTOList = studentServices.findStudentByFirstName(firstName);
        return ResponseEntity.ok(studentResponseDTOList);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<StudentRequestDTO> updateStudent(@PathVariable @RequestBody Long id, StudentResponseDTO student) {
        StudentRequestDTO studentToBeUpdated = studentServices.updateStudentById(id, student);
        return ResponseEntity.ok(studentToBeUpdated);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudentById(@PathVariable long id) {
        studentServices.deleteStudentById(id);
        return ResponseEntity.noContent().build();
    }

}
