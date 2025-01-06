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
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleEntityNotFoundException(EntityNotFoundException e) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.NOT_FOUND.value());
        response.put("error", "Not Found");
        response.put("message", e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(CreatingEntityException.class)
    public ResponseEntity<ErrorResponse> handleCantCreatingEntity(CreatingEntityException e, HttpServletRequest request) {
        String timestamp = DateTimeConfig.FORMATTER.format(Instant.now());

        ErrorResponse errorResponse = new ErrorResponse(
                timestamp,
                HttpStatus.BAD_REQUEST.value(),
                "Error creating entity",
                "A required field is missing or incorrect",
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
}
