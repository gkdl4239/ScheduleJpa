package com.example.schedulejpa.dto;


import com.example.schedulejpa.entity.User;
import lombok.Getter;

@Getter
public record SignUpResponseDto(
        Long id,
        String username,
        String email) {
    public static SignUpResponseDto toDto(User user) {
        return new SignUpResponseDto(
                user.getId(),
                user.getUsername(),
                user.getEmail()
        );
    }
}
