package com.dev.pilates.services.classes;

import com.dev.pilates.dtos.classes.utils.ClassesAddStudentsRequestDTO;
import com.dev.pilates.dtos.classes.utils.ClassesRemoveStudentResponseDTO;
import com.dev.pilates.dtos.classes.ClassesRequestDTO;
import com.dev.pilates.dtos.classes.ClassesResponseDTO;
import com.dev.pilates.entities.Classes;
import com.dev.pilates.entities.Professor;
import com.dev.pilates.entities.Student;
import com.dev.pilates.exceptions.CreatingEntityException;
import com.dev.pilates.repositories.ClassesRepository;
import com.dev.pilates.repositories.ProfessorRepository;
import com.dev.pilates.repositories.StudentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClassesServices {
    private final ClassesRepository classesRepository;
    private final ProfessorRepository professorRepository;
    private final StudentRepository studentRepository;

    public ClassesServices(ClassesRepository classesRepository, ProfessorRepository professorRepository, StudentRepository studentRepository) {
        this.classesRepository = classesRepository;
        this.professorRepository = professorRepository;
        this.studentRepository = studentRepository;
    }

    public ClassesRequestDTO save(ClassesRequestDTO classesRequestDTO) {
        try {
            Professor professor = professorRepository.findProfessorById(classesRequestDTO.professorId());
            List<Student> students = studentRepository.findAllById(classesRequestDTO.studentsId());
            Classes newClasses = classesRequestDTO.toClasses(professor, students);
            classesRepository.save(newClasses);
            return newClasses.toClassesRequestDTO();
        } catch (DataIntegrityViolationException e) {
            throw new CreatingEntityException(String.format("Error while creating classes: %s", e.getMessage()));
        }
    }

    public List<ClassesResponseDTO> findAll() {
        try {
            List<Classes> classesList = classesRepository.findAll();
            return classesList.stream().map(Classes::toClassesResponseDTO).collect(Collectors.toList());
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException(e.getMessage());
        }
    }

    public ClassesRemoveStudentResponseDTO removeStudentFromClasses(long classId, long studentId, long professorId) {
        Classes classes = classesRepository.findById(classId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Classes with id %s not found", classId)));
        if (!classes.getProfessor().getId().equals(professorId)) {
            throw new RuntimeException("Professor não autorizado a essa ação");
        }

        List<Student> updateList = new ArrayList<>(classes.getStudents()
                .stream()
                .filter(student -> !student.getId().equals(studentId))
                .toList());

        classes.setStudents(updateList);
        classes.setUpdated_at(LocalDateTime.now());
        classesRepository.save(classes);
        return ClassesRemoveStudentResponseDTO.fromClasses(classes);
    }

    public ClassesResponseDTO addStudentToClasses(long classId, long professorId, ClassesAddStudentsRequestDTO addStudentsRequestDTO) {
        Classes existingClass = classesRepository.findById(classId).orElseThrow(() -> new EntityNotFoundException("Aula não existe"));

        List<Student> existingStudents = studentRepository.findAllById(addStudentsRequestDTO.studentsIds());

        for (Student student : existingStudents) {
            if (!existingClass.getStudents().contains(student)) {
                existingClass.getStudents().add(student);
            }
        }

            if (!existingClass.getProfessor().getId().equals(professorId)) {
                throw new RuntimeException("Professor não autorizado a essa ação");
            }

        Classes updatedClass = classesRepository.save(existingClass);
        return updatedClass.toClassesResponseDTO();
    }
}
