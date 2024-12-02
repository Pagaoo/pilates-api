package com.dev.pilates.services;

import com.dev.pilates.dtos.student.StudentResponseDTO;
import com.dev.pilates.entities.Student;
import com.dev.pilates.repositories.StudentRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServices {
    private final StudentRepository studentRepository;

    public StudentServices(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    public Student findById(long id) {
        return studentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Aluno não encontrado"));
    }

    public List<StudentResponseDTO> findStudentByFirstName(String firstName) {
        List<StudentResponseDTO> students = studentRepository.findStudentByFirstName(firstName);

        if (students.isEmpty()) {
            throw new EntityNotFoundException(String.format("Alunos com nome: '%s' não encontrados", firstName));
        }
        return students;
    }

    public Student save(@Valid Student student) {
        try {
            return studentRepository.save(student);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Erro ao tentar salvar aluno", e.getCause());
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
}
