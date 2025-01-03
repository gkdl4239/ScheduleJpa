package com.example.schedulejpa.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public record SignUpRequestDto
        (
                @Size(max = 10, message = "이름은 10글자를 넘을 수 없습니다.")
                @NotBlank
                String username,
                @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$", message = "올바른 이메일형식을 입력하세요.")
                String email,
                String password) {

}
