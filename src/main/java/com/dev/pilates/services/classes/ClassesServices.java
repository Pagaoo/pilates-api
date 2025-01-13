package com.dev.pilates.services.classes;

import com.dev.pilates.dtos.classes.ClassesRequestDTO;
import com.dev.pilates.dtos.classes.ClassesResponseDTO;
import com.dev.pilates.dtos.classes.utils.ClassesAddOrRemoveStudentDTO;
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

    public ClassesResponseDTO removeStudentFromClasses(long classId, long professorId, ClassesAddOrRemoveStudentDTO removeStudentResponseDTO) {
        Classes classes = classesRepository.findById(classId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Classes with id %s not found", classId)));
        if (!classes.getProfessor().getId().equals(professorId)) {
            throw new RuntimeException("Professor não autorizado a essa ação");
        }

        List<Student> studentsToRemove = studentRepository.findAllById(removeStudentResponseDTO.studentsIds());
        classes.getStudents().removeIf(studentsToRemove::contains);
        classes.setUpdated_at(LocalDateTime.now());

        classesRepository.save(classes);
        return classes.toClassesResponseDTO();
    }

    public ClassesResponseDTO addStudentToClasses(long classId, long professorId, ClassesAddOrRemoveStudentDTO addStudentsRequestDTO) {
        Classes existingClass = classesRepository.findById(classId).orElseThrow(() -> new EntityNotFoundException("Aula não existe"));

        if (!existingClass.getProfessor().getId().equals(professorId)) {
            throw new RuntimeException("Professor não autorizado a esta ação");
        }

        List<Student> existingStudents = studentRepository.findAllById(addStudentsRequestDTO.studentsIds());

        for (Student student : existingStudents) {
            if (!existingClass.getStudents().contains(student)) {
                existingClass.getStudents().add(student);
            }
        }

        Classes updatedClass = classesRepository.save(existingClass);
        return updatedClass.toClassesResponseDTO();
    }
}
