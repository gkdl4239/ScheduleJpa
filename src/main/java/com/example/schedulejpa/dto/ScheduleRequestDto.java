package com.example.schedulejpa.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public record ScheduleRequestDto(
        @NotBlank String title,
        @NotBlank String contents) {

}
