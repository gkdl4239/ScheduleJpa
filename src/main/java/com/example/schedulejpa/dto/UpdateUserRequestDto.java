package com.example.schedulejpa.dto;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UpdateUserRequestDto {

    @Size(max = 10, message = "이름은 10글자를 넘을 수 없습니다.")
    private final String username;

    private final String oldPassword;

    @Size(min = 4, message = "비밀번호는 최소 4자리부터 사용 가능합니다.")
    private final String newPassword;

}
