package com.example.schedulejpa.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException e) {
        Map<String, String> error = new HashMap<>();
        FieldError firstError = e.getBindingResult().getFieldErrors().stream().findFirst().orElseThrow();
        error.put(firstError.getField(), firstError.getDefaultMessage());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Map<String,String>> handleResponseStatusException(ResponseStatusException e) {
        Map<String, String> error = new HashMap<>();
        error.put("error", e.getReason());
        return new ResponseEntity<>(error, e.getStatusCode());
    }
}
