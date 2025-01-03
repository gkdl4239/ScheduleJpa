package com.example.schedulejpa.service;

import com.example.schedulejpa.dto.PageResponseDto;
import com.example.schedulejpa.dto.ScheduleResponseDto;
import com.example.schedulejpa.dto.UserResponseDto;
import com.example.schedulejpa.entity.Schedule;
import com.example.schedulejpa.entity.User;
import com.example.schedulejpa.exception.CustomNotFoundException;
import com.example.schedulejpa.repository.ScheduleRepository;
import com.example.schedulejpa.handler.ExceptionHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final UserService userService;
    private final ExceptionHandler exceptionHandler;
    @Autowired
    @Lazy
    private CommentService commentService;

    @Transactional
    public ScheduleResponseDto save(Long id, String title, String contents) {

        User user = userService.findByIdOrElseThrow(id);
        Schedule schedule = new Schedule(title, contents, user);

        Schedule savedSchedule = scheduleRepository.save(schedule);
        Long countComment = commentService.countByScheduleId(schedule.getId());

        return ScheduleResponseDto.toDto(savedSchedule, countComment);
    }

    public ScheduleResponseDto findById(Long id) {

        Schedule foundSchedule = findByIdOrElseThrow(id);
        Long countComment = commentService.countByScheduleId(foundSchedule.getId());

        return ScheduleResponseDto.toDto(foundSchedule,countComment);
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

        Schedule schedule = findByIdOrElseThrow(id);

        exceptionHandler.checkSameId(schedule.getUser().getId(), loginUser.id(), "본인이 작성한 글이 아닙니다.");

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

        Schedule schedule = findByIdOrElseThrow(id);

        exceptionHandler.checkSameId(schedule.getUser().getId(), loginUser.id(), "본인이 작성한 글이 아닙니다.");

        scheduleRepository.delete(schedule);
    }

    public Schedule findByIdOrElseThrow(Long id) {
        return scheduleRepository.findById(id)
                .orElseThrow(() -> new CustomNotFoundException("존재하지 않는 일정입니다."));
    }
}
