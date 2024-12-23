package com.dev.pilates.dtos.student;

import com.dev.pilates.entities.Student;

import java.time.LocalDateTime;

public record StudentRequestDTO(String firstName, String lastName, Boolean is_active) {
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
