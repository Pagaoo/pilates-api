package com.dev.pilates.exceptions;

import com.dev.pilates.config.DateTimeConfig;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFoundException(EntityNotFoundException e,HttpServletRequest request) {
        String timestamp = DateTimeConfig.FORMATTER.format(Instant.now());

        ErrorResponse errorResponse = new ErrorResponse(
                timestamp,
                HttpStatus.NOT_FOUND.value(),
                "Not found",
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
                "bad request",
                e.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableJsonException(HttpServletRequest request) {
        String timestamp = DateTimeConfig.FORMATTER.format(Instant.now());

        ErrorResponse errorResponse = new ErrorResponse(
                timestamp,
                HttpStatus.BAD_REQUEST.value(),
                "bad request",
                "Invalid JSON format or missing required fields",
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception e, HttpServletRequest request) {
        String timestamp = DateTimeConfig.FORMATTER.format(Instant.now());
        ErrorResponse errorResponse = new ErrorResponse(
                timestamp,
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal Server Error",
                e.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}
