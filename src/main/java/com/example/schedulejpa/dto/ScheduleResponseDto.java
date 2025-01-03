package com.example.schedulejpa.dto;

import com.example.schedulejpa.entity.Schedule;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
public record ScheduleResponseDto(
        Long id,
        String username,
        String title,
        String contents,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        Long commentCount) {

    public static ScheduleResponseDto toDto(Schedule schedule, Long countComment) {
        return new ScheduleResponseDto(
                schedule.getId(),
                schedule.getUser().getUsername(),
                schedule.getTitle(),
                schedule.getContents(),
                schedule.getCreatedAt(),
                schedule.getUpdatedAt(),
                countComment);
    }

}
