package com.example.schedulejpa.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public record ErrorResponse(
        LocalDateTime timestamp,
        int status,
        String error,
        String message) {
}
