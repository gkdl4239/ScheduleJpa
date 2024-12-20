package com.example.schedulejpa.service;

import com.example.schedulejpa.dto.PageResponseDto;
import com.example.schedulejpa.dto.ScheduleResponseDto;
import com.example.schedulejpa.dto.UserResponseDto;
import com.example.schedulejpa.entity.Schedule;
import com.example.schedulejpa.entity.User;
import com.example.schedulejpa.repository.ScheduleRepository;
import com.example.schedulejpa.handler.ExceptionHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final UserService userService;
    private final ExceptionHandler exceptionHandler;
    @Autowired
    @Lazy
    private CommentService commentService;

    public ScheduleResponseDto save(Long id, String title, String contents) {

        User user = userService.findByIdOrElseThrow(id);
        Schedule schedule = new Schedule(title, contents, user);

        Schedule savedSchedule = scheduleRepository.save(schedule);
        Long countComment = commentService.countByScheduleId(schedule.getId());

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
        Long countComment = commentService.countByScheduleId(findId.getId());

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

        Page<ScheduleResponseDto> page = scheduleRepository.findAllScheduleAndCommentCount(pageable);

        return new PageResponseDto<>(
                page.getContent(),
                page.getNumber() + 1,
                page.getTotalPages()

        );
    }

    @Transactional
    public void update(Long id, String title, String contents, UserResponseDto loginUser) {

        Schedule schedule = scheduleRepository.findByIdOrElseThrow(id);

        exceptionHandler.checkSameId(schedule.getUser().getId(), loginUser.getId(), "본인이 작성한 글이 아닙니다.");

        if (title == null) {
            title = schedule.getTitle();
        }

        if (contents == null) {
            contents = schedule.getContents();
        }

        schedule.setTitleAndContents(title, contents);

    }

    @Transactional
    public void delete(Long id, UserResponseDto loginUser) {

        Schedule schedule = scheduleRepository.findByIdOrElseThrow(id);

        exceptionHandler.checkSameId(schedule.getUser().getId(), loginUser.getId(), "본인이 작성한 글이 아닙니다.");

        scheduleRepository.delete(schedule);
    }

    public Schedule findByIdOrElseThrow(Long id) {
        return scheduleRepository.findByIdOrElseThrow(id);
    }
}
