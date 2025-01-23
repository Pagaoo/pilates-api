package com.dev.pilates.exceptions;

import com.dev.pilates.config.DateTimeConfig;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.management.relation.RoleNotFoundException;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFoundException(EntityNotFoundException e,HttpServletRequest request) {
        String timestamp = DateTimeConfig.FORMATTER.format(Instant.now());

        ErrorResponse errorResponse = new ErrorResponse(
                timestamp,
                HttpStatus.NOT_FOUND.value(),
                "Entity not found",
                e.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(CreatingEntityException.class)
    public ResponseEntity<ErrorResponse> handleCantCreatingEntity(CreatingEntityException e, HttpServletRequest request) {
        String timestamp = DateTimeConfig.FORMATTER.format(Instant.now());

        ErrorResponse errorResponse = new ErrorResponse(
                timestamp,
                HttpStatus.BAD_REQUEST.value(),
                "Error creating entity",
                e.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
}
