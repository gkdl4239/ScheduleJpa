package com.example.schedulejpa.service;

import com.example.schedulejpa.dto.PageResponseDto;
import com.example.schedulejpa.dto.ScheduleResponseDto;
import com.example.schedulejpa.dto.UserResponseDto;
import com.example.schedulejpa.entity.Schedule;
import com.example.schedulejpa.entity.User;
import com.example.schedulejpa.repository.CommentRepository;
import com.example.schedulejpa.repository.ScheduleRepository;
import com.example.schedulejpa.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;


@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    public ScheduleResponseDto save(Long id, String title, String contents) {

        User user = userRepository.findByIdOrElseThrow(id);
        Schedule schedule = new Schedule(title,contents,user);

        Schedule savedSchedule = scheduleRepository.save(schedule);
        Long countComment = commentRepository.countByScheduleId(schedule.getId());

        return new ScheduleResponseDto(
                savedSchedule.getId(),
                savedSchedule.getUser().getUsername(),
                savedSchedule.getTitle(),
                savedSchedule.getContents(),
                savedSchedule.getCreatedAt(),
                savedSchedule.getUpdatedAt(),
                countComment);
    }

    public ScheduleResponseDto findById(Long id) {

        Schedule findId = scheduleRepository.findByIdOrElseThrow(id);
        Long countComment = commentRepository.countByScheduleId(findId.getId());

        return new ScheduleResponseDto(
                findId.getId(),
                findId.getUser().getUsername(),
                findId.getTitle(),
                findId.getContents(),
                findId.getCreatedAt(),
                findId.getUpdatedAt(),
                countComment);
    }

    public PageResponseDto<ScheduleResponseDto> findAll(Pageable pageable) {

        Page<ScheduleResponseDto> page = scheduleRepository.findAll(pageable)
                .map( schedule -> {
                    Long commentCount = commentRepository.countByScheduleId(schedule.getId());
                    return ScheduleResponseDto.toDto(schedule,commentCount);
                });

        return new PageResponseDto<>(
                page.getContent(),
                page.getNumber() + 1,
                page.getTotalPages()

        );
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
