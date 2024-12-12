package com.example.schedulejpa.dto;

import com.example.schedulejpa.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto {

    private final String username;

    private final String contents;

    private final LocalDateTime createdAt;

    private final LocalDateTime updatedAt;

    public CommentResponseDto(String username, String contents, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.username = username;
        this.contents = contents;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static CommentResponseDto toDto(Comment comment) {
        return new CommentResponseDto(
                comment.getUser().getUsername(),
                comment.getContents(),
                comment.getCreatedAt(),
                comment.getUpdatedAt());
    }
}
