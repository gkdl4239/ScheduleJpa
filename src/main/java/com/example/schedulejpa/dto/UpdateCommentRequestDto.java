package com.example.schedulejpa.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UpdateCommentRequestDto {

    @Pattern(regexp = "^\\S.*$", message = "공백일 수 없습니다.")
    private final String contents;


}
