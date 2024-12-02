package com.dev.pilates.dtos.student;

public record StudentResponseDTO(Long id, String firstName, String lastName, Long role_id, Boolean is_active) {
}
