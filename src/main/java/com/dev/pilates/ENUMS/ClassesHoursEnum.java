package com.dev.pilates.ENUMS;

import lombok.Getter;

import java.time.LocalTime;

@Getter
public enum ClassesHoursEnum {
    OITO_HORAS(LocalTime.of(8,0)),
    NOVE_HORAS(LocalTime.of(9,0)),
    DEZ_HORAS(LocalTime.of(10,0)),
    QUARTORZE_HORAS(LocalTime.of(14,0)),
    QUINZE_HORAS(LocalTime.of(15,0)),
    DEZESSEIS_HORAS(LocalTime.of(16,0)),
    DEZESSETE_HORAS(LocalTime.of(17,0)),
    DEZOITO_HORAS(LocalTime.of(18,0)),
    DEZENOVE_HORAS(LocalTime.of(19,0)),
    VINTE_HORAS(LocalTime.of(20,0));

    private final LocalTime hour;

    ClassesHoursEnum(LocalTime hour) {
        this.hour = hour;
    }
}
