package com.example.schedulejpa.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SignUpRequestDto {

    @Size(max = 10, message = "이름은 10글자를 넘을 수 없습니다.")
    @NotBlank
    private final String username;

    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$", message = "올바른 이메일형식을 입력하세요.")
    private final String email;

    @Size(min = 4, message = "비밀번호는 최소 4자리부터 사용 가능합니다.")
    private final String password;
}
