package com.dev.pilates.dtos.classes;

import java.util.List;

public record ClassesAddStudentsRequestDTO(List<Long> studentsIds) {
}
