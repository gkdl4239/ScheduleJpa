package com.example.schedulejpa.dto;

import com.example.schedulejpa.entity.Schedule;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ScheduleResponseDto {

    private String username;

    private String title;

    private String contents;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public static ScheduleResponseDto toDto(Schedule schedule){
        return new ScheduleResponseDto(
                schedule.getUser().getUsername(),
                schedule.getTitle(),
                schedule.getContents(),
                schedule.getCreatedAt(),
                schedule.getUpdatedAt());
    }

}
