package com.dev.pilates.dtos.student;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record StudentResponseDTO(Long id,
                                 @NotBlank(message = "O primeiro nome não pode estar vazio")
                                 @Size(min = 3, message = "O primeiro nome tem que ter no minímo 3 caracteres.")
                                 String firstName,
                                 @NotBlank(message = "O sobrenome não pode estar vazio")
                                 @Size(min = 3, message = "O sobrenome tem que ter no minímo 3 caracteres.")
                                 String lastName,
                                 @NotNull(message = "O status do aluno não pode estar vazio")
                                 Boolean is_active) {
}
