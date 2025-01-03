package com.example.schedulejpa.dto;

import com.example.schedulejpa.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public record CommentResponseDto(
        Long id,
        String username,
        String contents,
        LocalDateTime createdAt,
        LocalDateTime updatedAt) {

    public static CommentResponseDto toDto(Comment comment) {
        return new CommentResponseDto(
                comment.getId(),
                comment.getUser().getUsername(),
                comment.getContents(),
                comment.getCreatedAt(),
                comment.getUpdatedAt());
    }
}
