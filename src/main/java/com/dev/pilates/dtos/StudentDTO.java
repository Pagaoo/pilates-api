package com.dev.pilates.dtos;

import java.time.LocalDateTime;

public record StudentDTO(String firstName, String lastName, Long role_id, Boolean is_active, LocalDateTime created_at,
                         LocalDateTime updated_at) {
}
