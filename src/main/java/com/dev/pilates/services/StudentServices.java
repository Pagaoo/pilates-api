package com.dev.pilates.services;

import com.dev.pilates.dtos.student.StudentRequestDTO;
import com.dev.pilates.dtos.student.StudentResponseDTO;
import com.dev.pilates.entities.Roles;
import com.dev.pilates.entities.Student;
import com.dev.pilates.repositories.RolesRepository;
import com.dev.pilates.repositories.StudentRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.management.relation.RoleNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentServices {
    private final StudentRepository studentRepository;
    private final RolesRepository rolesRepository;

    public StudentServices(StudentRepository studentRepository, RolesRepository rolesRepository) {
        this.studentRepository = studentRepository;
        this.rolesRepository = rolesRepository;
    }

    public List<StudentResponseDTO> findAll() {
        List<Student> students = studentRepository.findAll();
        List<StudentResponseDTO> studentResponseDTOList = students.stream().map(Student::toStudentResponseDTO).collect(Collectors.toList());
        return studentResponseDTOList;
    }

    public StudentResponseDTO findById(long id) {
        Student student = studentRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Aluno não encontrado"));
        return student.toStudentResponseDTO();
    }

    public List<StudentResponseDTO> findStudentByFirstName(String firstName) {
        List<StudentResponseDTO> students = studentRepository.findStudentByFirstName(firstName);

        if (students.isEmpty()) {
            throw new EntityNotFoundException(String.format("Alunos com nome: '%s' não encontrados", firstName));
        }
        return students;
    }

    public StudentRequestDTO save(@Valid StudentRequestDTO studentRequestDTO) {
        try {
            Roles role = rolesRepository.findById(studentRequestDTO.role_id())
                    .orElseThrow(() -> new RoleNotFoundException("Role not found: " + studentRequestDTO.role_id()));

            Student newStudent = convertoToStudentRequestDTO(studentRequestDTO, role);
            Student savedStudent = studentRepository.save(newStudent);
            return savedStudent.toStudentRequestDTO();
        } catch (RoleNotFoundException e) {
            throw new RuntimeException("Erro ao salvar aluno, role de nome: %s não existe", e.getCause());
        }
    }

    public void deleteStudentById(long id) {
        if (!studentRepository.existsById(id)) {
            throw new EntityNotFoundException(String.format("Aluno com ID: %s não encontrado para deleção", id));
        }
        try {
            studentRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Erro de integridade ao tentar deletar aluno", e);
        }
    }

    public Student updateStudentById(long id, StudentResponseDTO studentResponseDTO) {
        Student existingStudent = studentRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(String.format("Aluno de id: %s não encontrado", id)));

        existingStudent.setFirstName(studentResponseDTO.firstName());
        existingStudent.setLastName(studentResponseDTO.lastName());
        Roles role  = rolesRepository.findById(studentResponseDTO.role_id()).orElseThrow(() ->
                new EntityNotFoundException(String.format("Role de id: %s não encontrada", studentResponseDTO.role_id())));
        existingStudent.setRole(role);
        existingStudent.setIs_active(studentResponseDTO.is_active());
        existingStudent.setUpdated_at(LocalDateTime.now());
        return studentRepository.save(existingStudent);
    }


    private Student convertoToStudentRequestDTO(StudentRequestDTO studentRequestDTO, Roles role) {
        Student student = new Student();
        student.setFirstName(studentRequestDTO.firstName());
        student.setLastName(studentRequestDTO.lastName());
        student.setRole(role);
        student.setIs_active(studentRequestDTO.is_active());
        student.setCreated_at(LocalDateTime.now());
        student.setUpdated_at(LocalDateTime.now());
        return student;
    }
}
