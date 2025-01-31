package com.dev.pilates.services.student;

import com.dev.pilates.dtos.student.StudentRequestDTO;
import com.dev.pilates.dtos.student.StudentResponseDTO;
import com.dev.pilates.entities.Classes;
import com.dev.pilates.entities.Student;
import com.dev.pilates.exceptions.CreatingEntityException;
import com.dev.pilates.repositories.StudentRepository;
import com.dev.pilates.specifications.StudentSpecifications;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

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
        try {
            List<Student> students = studentRepository.findAll();
            return students.stream().map(Student::toStudentResponseDTO).collect(Collectors.toList());
        } catch (RuntimeException e) {
            throw new EntityNotFoundException("Alunos não encontrado");
        }
    }

    public StudentResponseDTO findStudentById(long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Aluno não encontrado"));
        return student.toStudentResponseDTO();
    }

    public List<StudentResponseDTO> findStudentsByName(String name) {
        try {
            Specification<Student> specification = StudentSpecifications.studentNameContainsIgnoreCase(name);
            return studentRepository.findAll(specification).stream().map(Student::toStudentResponseDTO).collect(Collectors.toList());
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Transactional(rollbackOn = RuntimeException.class)
    public StudentRequestDTO save(@Valid StudentRequestDTO studentRequestDTO) {
        try {
            Student newStudent = studentRequestDTO.toStudent();
            Student savedStudent = studentRepository.save(newStudent);
            return savedStudent.toStudentRequestDTO();
        } catch (RuntimeException e) {
            throw new CreatingEntityException("Erro ao salvar aluno");
        }
    }

    public void deleteStudentById(long id) {
        Student studentToBeDeleted = studentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Aluno não encontrado"));
        try {
            for (Classes classes : studentToBeDeleted.getClassesList()) {
                classes.getStudents().remove(studentToBeDeleted);
            }
            studentRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Erro de integridade ao tentar deletar aluno", e);
        }
    }
    
    public StudentRequestDTO updateStudentById(long id, StudentRequestDTO studentRequestDTO) {
        Student existingStudent = studentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format("Aluno de ID: %s não encontrado para atualizar", id)));
        try {
            BeanUtils.copyProperties(studentRequestDTO, existingStudent, "id", "created_at");
            existingStudent.setUpdated_at(LocalDateTime.now());
            Student updateStudent = studentRepository.save(existingStudent);
            return updateStudent.toStudentRequestDTO();
        } catch(RuntimeException e) {
            throw new RuntimeException("Erro ao atualizar aluno", e);
        }
    }
}
