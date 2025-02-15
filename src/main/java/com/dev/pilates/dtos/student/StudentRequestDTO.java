package com.dev.pilates.dtos.student;

import com.dev.pilates.entities.Student;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record StudentRequestDTO(
        @NotBlank(message = "O primeiro nome não pode estar vazio")
        @Size(min = 3, message = "O primeiro nome tem que ter no minímo 3 caracteres.")
        String firstName,
        @NotBlank(message = "O sobrenome não pode estar vazio")
        @Size(min = 3, message = "O sobrenome tem que ter no minímo 3 caracteres.")
        String lastName,
        @NotNull(message = "O status do aluno não pode estar vazio")
        Boolean is_active) {
    public Student toStudent() {
        Student student = new Student();
        student.setFirstName(firstName);
        student.setLastName(lastName);
        student.setIs_active(is_active);
        student.setCreated_at(LocalDateTime.now());
        student.setUpdated_at(LocalDateTime.now());
        return student;
    }
}
