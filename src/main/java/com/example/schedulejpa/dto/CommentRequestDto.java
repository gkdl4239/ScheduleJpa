package com.example.schedulejpa.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public record CommentRequestDto(
        @NotBlank String contents) {

}
