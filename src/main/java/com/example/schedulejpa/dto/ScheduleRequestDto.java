package com.example.schedulejpa.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ScheduleRequestDto {

    @NotBlank
    private final String title;

    @NotBlank
    private final String contents;
}
