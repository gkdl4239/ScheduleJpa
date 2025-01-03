package com.example.schedulejpa.exception;

public class CustomBadRequestException extends RuntimeException {
    public CustomBadRequestException (String message) {
        super(message);
    }
}
