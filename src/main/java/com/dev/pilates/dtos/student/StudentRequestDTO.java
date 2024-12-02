package com.dev.pilates.dtos.student;

public record StudentRequestDTO(String firstName, String lastName, Long role_id, Boolean is_active) {
}
