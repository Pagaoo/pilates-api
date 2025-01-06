package com.dev.pilates.config;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class DateTimeConfig {
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter
            .ofPattern("dd/MM/yyyy HH:mm:ss")
            .withZone(ZoneId.of("America/Sao_Paulo"));
}
