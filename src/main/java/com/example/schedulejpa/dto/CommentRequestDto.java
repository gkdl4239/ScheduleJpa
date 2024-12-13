package com.example.schedulejpa.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CommentRequestDto {

    @NotBlank
    private final String contents;

    public CommentRequestDto(String contents) {
        this.contents = contents;
    }
}
