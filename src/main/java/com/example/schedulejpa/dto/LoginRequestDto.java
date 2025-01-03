package com.example.schedulejpa.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public record LoginRequestDto(
        @Email @NotBlank String email,
        @NotBlank String password) {

}
