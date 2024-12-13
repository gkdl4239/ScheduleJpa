package com.example.schedulejpa.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class ScheduleRequestDto {

    @NotBlank
    private final String title;

    @NotBlank
    private final String contents;

    public ScheduleRequestDto(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }
}
