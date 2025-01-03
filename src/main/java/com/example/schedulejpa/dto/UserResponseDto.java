package com.example.schedulejpa.dto;

import com.example.schedulejpa.entity.User;
import lombok.Getter;

@Getter
public record UserResponseDto(
        Long id,
        String username,
        String email) {
    public static UserResponseDto toDto(User user) {
        return new UserResponseDto(
                user.getId(),
                user.getUsername(),
                user.getEmail()
        );
    }
}
