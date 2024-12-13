package com.example.schedulejpa.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class LoginRequestDto {

    @Email
    @NotBlank
    private final String email;

    @NotBlank
    private final String password;
}
