package com.dev.pilates.services;

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

    public List<Student> findStudentByFirstName(String firstName) {
        List<Student> students = studentRepository.findStudentByFirstName(firstName);

        if (students.isEmpty()) {
            throw new EntityNotFoundException(String.format("Alunos com nome: '%s' não encontrados", firstName));
        }
        return students;
    }

    public Student save(@Valid Student student) {
        try {
            return studentRepository.save(student);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Erro ao tentar salvar", e.getCause());
        }
    }
}
