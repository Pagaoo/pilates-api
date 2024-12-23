package com.dev.pilates.services.student;

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

    public StudentServices(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<StudentResponseDTO> findAll() {
        List<Student> students = studentRepository.findAll();
        return students.stream().map(Student::toStudentResponseDTO).collect(Collectors.toList());
    }

    public StudentResponseDTO findById(long id) {
        Student student = studentRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Aluno não encontrado"));
        return student.toStudentResponseDTO();
    }

    //ajeitar para voltar uma lista de alunos que tenham a string pesquisada no nome.
    // Exemplo: Joao, voltar joao pedro, joao vitor.
    // Exemplo 2: an, voltar ana, anastacia e etc
    //
    public List<StudentResponseDTO> findStudentByFirstName(String firstName) {
        List<StudentResponseDTO> students = studentRepository.findStudentByFirstName(firstName);

        if (students.isEmpty()) {
            throw new EntityNotFoundException(String.format("Alunos com nome: '%s' não encontrados", firstName));
        }
        return students;
    }

    public StudentRequestDTO save(@Valid StudentRequestDTO studentRequestDTO) {
        try {
            Student newStudentToDto = convertToStudentRequestDTO(studentRequestDTO);
            Student saveStudent = studentRepository.save(newStudentToDto);
            return saveStudent.toStudentRequestDTO();
        } catch (RuntimeException e) {
            throw new RuntimeException("Erro ao salvar aluno", e.getCause());
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


    //ver pq não ta funcionando
    public StudentRequestDTO updateStudentById(long id, StudentResponseDTO studentResponseDTO) {
        StudentResponseDTO existingStudent = findById(id);

        Student studentToUpdate = convertToStudentResponseDTO(existingStudent);
        Student updatedStudent = studentRepository.save(studentToUpdate);
        return studentRepository.save(updatedStudent).toStudentRequestDTO();
    }


    private Student convertToStudentRequestDTO(StudentRequestDTO studentRequestDTO) {
        Student studentRequest = new Student();
        studentRequest.setFirstName(studentRequestDTO.firstName());
        studentRequest.setLastName(studentRequestDTO.lastName());
        studentRequest.setIs_active(studentRequestDTO.is_active());
        studentRequest.setCreated_at(LocalDateTime.now());
        studentRequest.setUpdated_at(LocalDateTime.now());
        return studentRequest;
    }

    private Student convertToStudentResponseDTO(StudentResponseDTO studentResponseDTO) {
        Student studentResponse = new Student();
        studentResponse.setId(studentResponseDTO.id());
        studentResponse.setFirstName(studentResponseDTO.firstName());
        studentResponse.setLastName(studentResponseDTO.lastName());
        studentResponse.setIs_active(studentResponseDTO.is_active());
        studentResponse.setUpdated_at(LocalDateTime.now());
        return studentResponse;
    }
}
