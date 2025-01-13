package com.dev.pilates.dtos.classes;

import com.dev.pilates.entities.Classes;
import com.dev.pilates.entities.Student;

import java.util.List;
import java.util.stream.Collectors;

public record ClassesRemoveStudentResponseDTO(Long professorId, List<Long> studentId) {
    public static ClassesRemoveStudentResponseDTO fromClasses(Classes classes) {
        List<Long> students = classes.getStudents().stream().map(Student::getId).collect(Collectors.toList());
        return new ClassesRemoveStudentResponseDTO(classes.getProfessor().getId(), students);
    }
}
