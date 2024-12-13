package com.example.schedulejpa.dto;

import com.example.schedulejpa.entity.Comment;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class CommentResponseDto {

    private final Long id;

    private final String username;

    private final String contents;

    private final LocalDateTime createdAt;

    private final LocalDateTime updatedAt;

    public static CommentResponseDto toDto(Comment comment) {
        return new CommentResponseDto(
                comment.getId(),
                comment.getUser().getUsername(),
                comment.getContents(),
                comment.getCreatedAt(),
                comment.getUpdatedAt());
    }
}
