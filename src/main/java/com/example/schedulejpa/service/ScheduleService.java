package com.example.schedulejpa.service;

import com.example.schedulejpa.dto.ScheduleRequestDto;
import com.example.schedulejpa.dto.ScheduleResponseDto;
import com.example.schedulejpa.dto.UserResponseDto;
import com.example.schedulejpa.entity.Schedule;
import com.example.schedulejpa.entity.User;
import com.example.schedulejpa.repository.ScheduleRepository;
import com.example.schedulejpa.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;

    public ScheduleResponseDto save(Long id, String title, String contents) {

        User user = userRepository.findByIdOrElseThrow(id);
        Schedule schedule = new Schedule(title,contents,user);

        Schedule savedSchedule = scheduleRepository.save(schedule);

        return new ScheduleResponseDto(savedSchedule.getUser().getUsername(),
                savedSchedule.getTitle(),
                savedSchedule.getContents(),
                savedSchedule.getCreatedAt(),
                savedSchedule.getUpdatedAt());
    }

    public ScheduleResponseDto findById(Long id) {

        Schedule findId = scheduleRepository.findByIdOrElseThrow(id);

        return new ScheduleResponseDto(findId.getUser().getUsername(),
                findId.getTitle(),
                findId.getContents(),
                findId.getCreatedAt(),
                findId.getUpdatedAt());
    }

    public List<ScheduleResponseDto> findAll() {
        return scheduleRepository.findAll().stream()
                .map(ScheduleResponseDto::toDto)
                .toList();
    }

    @Transactional
    public void update(Long id, String title, String contents, UserResponseDto loginUser) {

        Schedule schedule = scheduleRepository.findByIdOrElseThrow(id);

        if(!Objects.equals(schedule.getUser().getId(), loginUser.getId())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "본인이 작성한 글이 아닙니다!");
        }

        if(title == null) {
            title = schedule.getTitle();
        }

        if(contents == null) {
            contents = schedule.getContents();
        }

        schedule.setTitleAndContents(title, contents);

    }

    @Transactional
    public void delete(Long id, UserResponseDto loginUser) {

        Schedule schedule = scheduleRepository.findByIdOrElseThrow(id);

        if(!Objects.equals(schedule.getUser().getId(), loginUser.getId())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "본인이 작성한 글이 아닙니다!");
        }

        scheduleRepository.delete(schedule);
    }
}
