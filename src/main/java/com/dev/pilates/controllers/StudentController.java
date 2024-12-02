package com.dev.pilates.controllers;

import com.dev.pilates.dtos.student.StudentRequestDTO;
import com.dev.pilates.dtos.student.StudentResponseDTO;
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
    public ResponseEntity<Student> createStudent(@RequestBody @Valid StudentRequestDTO studentRequestDTO) throws RoleNotFoundException {
        Roles role = rolesRepository.findById(studentRequestDTO.role_id())
                .orElseThrow(() -> new RoleNotFoundException("Role not found: " + studentRequestDTO.role_id()));

        Student student = convertoToStudentRequestDTO(studentRequestDTO, role);
        Student savedStudent = studentServices.save(student);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedStudent);
    }

    @GetMapping
    public ResponseEntity<List<StudentResponseDTO>> getAllStudents() {
        List<Student> students = studentServices.findAll();

        List<StudentResponseDTO> studentResponseDTOList = students.stream().map(Student::toStudentResponseDTO).collect(Collectors.toList());

        return ResponseEntity.ok(studentResponseDTOList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable long id) {
        Student student =  studentServices.findById(id);
        return ResponseEntity.ok(student);
    }

    @GetMapping("name/{firstName}")
    public ResponseEntity<List<StudentResponseDTO>> getStudentsByFirstName(@PathVariable String firstName) {
        List<StudentResponseDTO> studentResponseDTOList = studentServices.findStudentByFirstName(firstName);
        return ResponseEntity.ok(studentResponseDTOList);
    }


    private Student convertoToStudentRequestDTO(StudentRequestDTO studentRequestDTO, Roles role) {
        Student student = new Student();
        student.setFirstName(studentRequestDTO.firstName());
        student.setLastName(studentRequestDTO.lastName());
        student.setRole_id(role);
        student.setIs_active(studentRequestDTO.is_active());
        student.setCreated_at(studentRequestDTO.created_at());
        student.setUpdated_at(studentRequestDTO.updated_at());
        return student;
    }
}
