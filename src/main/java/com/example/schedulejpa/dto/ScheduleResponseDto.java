package com.example.schedulejpa.dto;

import com.example.schedulejpa.entity.Schedule;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class ScheduleResponseDto {

    private final Long id;

    private final String username;

    private final String title;

    private final String contents;

    private final LocalDateTime createdAt;

    private final LocalDateTime updatedAt;

    private final Long commentCount;

    public static ScheduleResponseDto toDto(Schedule schedule, Long commentCount){
        return new ScheduleResponseDto(
                schedule.getId(),
                schedule.getUser().getUsername(),
                schedule.getTitle(),
                schedule.getContents(),
                schedule.getCreatedAt(),
                schedule.getUpdatedAt(),
                commentCount);
    }

}
