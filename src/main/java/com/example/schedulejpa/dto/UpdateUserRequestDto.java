package com.example.schedulejpa.dto;

import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public record UpdateUserRequestDto(
        @Size(max = 10, message = "이름은 10글자를 넘을 수 없습니다.")
        String username,

        String oldPassword,
        @Size(min = 4, message = "비밀번호는 최소 4자리부터 사용 가능합니다.")
        String newPassword) {

}
