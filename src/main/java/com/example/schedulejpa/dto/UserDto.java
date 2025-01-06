package com.example.schedulejpa.dto;

import com.example.schedulejpa.entity.User;
import lombok.Getter;

@Getter
public record UserDto(
        Long id,
        String username,
        String email) {
    public static UserDto toDto(User user) {
        return new UserDto(
                user.getId(),
                user.getUsername(),
                user.getEmail()
        );
    }
}
