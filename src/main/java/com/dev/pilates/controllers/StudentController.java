package com.dev.pilates.controllers;

import com.dev.pilates.dtos.StudentDTO;
import com.dev.pilates.entities.Roles;
import com.dev.pilates.entities.Student;
import com.dev.pilates.repositories.RolesRepository;
import com.dev.pilates.services.StudentServices;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.management.relation.RoleNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/student")
public class StudentController {

    private final StudentServices studentServices;
    private final RolesRepository rolesRepository;

    public StudentController(StudentServices studentServices, RolesRepository rolesRepository) {
        this.studentServices = studentServices;
        this.rolesRepository = rolesRepository;
    }

    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody @Valid StudentDTO studentDTO) throws RoleNotFoundException {
        Roles role = rolesRepository.findById(studentDTO.role_id())
                .orElseThrow(() -> new RoleNotFoundException("Role not found: " + studentDTO.role_id()));

        Student student = convertoToDTO(studentDTO, role);
        Student savedStudent = studentServices.save(student);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedStudent);
    }

    @GetMapping
    public ResponseEntity<List<StudentDTO>> getAllStudents() {
        List<Student> students = studentServices.findAll();

        List<StudentDTO> studentDTOs = students.stream().map(Student::toDTO).collect(Collectors.toList());

        return ResponseEntity.ok(studentDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable long id) {
        Student student =  studentServices.findById(id);
        return ResponseEntity.ok(student);
    }


    private Student convertoToDTO(StudentDTO studentDTO, Roles role) {
        Student student = new Student();
        student.setFirstName(studentDTO.firstName());
        student.setLastName(studentDTO.lastName());
        student.setRole_id(role);
        student.setIs_active(studentDTO.is_active());
        student.setCreated_at(studentDTO.created_at());
        student.setUpdated_at(studentDTO.updated_at());
        return student;
    }
}
