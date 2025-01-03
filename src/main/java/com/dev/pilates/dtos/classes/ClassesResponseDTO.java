package com.dev.pilates.dtos.classes;

import com.dev.pilates.ENUMS.ClassesHoursEnum;
import com.dev.pilates.ENUMS.WeekDaysEnum;

import java.util.List;

public record ClassesResponseDTO(Long id, Long professorId, List<Long> studentsId,
                                 WeekDaysEnum weekDaysEnum, ClassesHoursEnum classesHoursEnum) {
}
