package com.dev.pilates.dtos.student;

import java.time.LocalDateTime;

public record StudentRequestDTO(String firstName, String lastName, Long role_id, Boolean is_active, LocalDateTime created_at,
                                LocalDateTime updated_at) {
}
