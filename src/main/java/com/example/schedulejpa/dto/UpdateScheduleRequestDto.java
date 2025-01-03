package com.example.schedulejpa.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public record UpdateScheduleRequestDto(
        @Pattern(regexp = "^\\S.*$", message = "공백일 수 없습니다.")
        String title,
        @Pattern(regexp = "^\\S.*$", message = "공백일 수 없습니다.")
        String contents) {

}
