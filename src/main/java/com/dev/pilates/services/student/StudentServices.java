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
import org.springframework.http.converter.HttpMessageNotReadableException;
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

    @Transactional(rollbackOn = RuntimeException.class)
    public List<StudentResponseDTO> findAll() {
        try {
            List<Student> students = studentRepository.findAll();
            return students.stream().map(Student::toStudentResponseDTO).collect(Collectors.toList());
        } catch (RuntimeException e) {
            throw new EntityNotFoundException("Alunos não encontrados");
        }
    }

    @Transactional(rollbackOn = RuntimeException.class)
    public StudentResponseDTO findStudentById(long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Aluno não encontrado"));
        return student.toStudentResponseDTO();
    }

    @Transactional(rollbackOn = {RuntimeException.class, EntityNotFoundException.class})
    public List<StudentResponseDTO> findStudentsByName(String name) {
        Specification<Student> specification = StudentSpecifications.studentNameContainsIgnoreCase(name);
        List<Student> students = studentRepository.findAll(specification);

        if (students.isEmpty()) {
            throw new EntityNotFoundException(String.format("Alunos com nome: %s não encontrados", name));
        }
        return students.stream().map(Student::toStudentResponseDTO).collect(Collectors.toList());
    }

    @Transactional(rollbackOn = RuntimeException.class)
    public StudentRequestDTO save(@Valid StudentRequestDTO studentRequestDTO) {
        try {
            Student newStudent = studentRequestDTO.toStudent();
            Student savedStudent = studentRepository.save(newStudent);
            return savedStudent.toStudentRequestDTO();
        } catch (HttpMessageNotReadableException e) {
            throw new HttpMessageNotReadableException(e.getMessage());
        } catch (RuntimeException e) {
            throw new CreatingEntityException("Erro ao criar aluno");
        }
    }

    public void deleteStudentById(long id) {
        Student studentToBeDeleted = studentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Aluno não encontrado"));
        for (Classes classes : studentToBeDeleted.getClassesList()) {
            classes.getStudents().remove(studentToBeDeleted);
        }
        studentRepository.deleteById(id);
    }

    @Transactional(rollbackOn = RuntimeException.class)
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
