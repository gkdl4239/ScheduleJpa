package com.example.schedulejpa.service;

import com.example.schedulejpa.dto.CommentResponseDto;
import com.example.schedulejpa.dto.UserResponseDto;
import com.example.schedulejpa.entity.Comment;
import com.example.schedulejpa.entity.Schedule;
import com.example.schedulejpa.entity.User;
import com.example.schedulejpa.exception.CustomNotFoundException;
import com.example.schedulejpa.repository.CommentRepository;
import com.example.schedulejpa.handler.ExceptionHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final ExceptionHandler exceptionHandler;
    private final UserService userService;
    @Autowired
    @Lazy
    private ScheduleService scheduleService;

    @Transactional
    public CommentResponseDto save(Long scheduleId, String contents, UserResponseDto loginUser) {

        User user = userService.findByIdOrElseThrow(loginUser.id());
        Schedule schedule = scheduleService.findByIdOrElseThrow(scheduleId);
        Comment comment = new Comment(contents, user, schedule);

        Comment savedComment = commentRepository.save(comment);

        return CommentResponseDto.toDto(savedComment);

    }

    public List<CommentResponseDto> findAll(Long scheduleId) {

        scheduleService.findByIdOrElseThrow(scheduleId);

        return commentRepository.findByScheduleId(scheduleId, Sort.by(Sort.Direction.DESC, "updatedAt")).stream()
                .map(CommentResponseDto::toDto)
                .toList();

    }

    @Transactional
    public void update(Long commentId, String contents, UserResponseDto loginUser) {

        Comment comment = findByIdOrElseThrow(commentId);

        exceptionHandler.checkSameId(comment.getUser().getId(), loginUser.id(), "본인이 작성한 댓글이 아닙니다.");

        if(contents == null) {
            contents = comment.getContents();
        }

        comment.setContents(contents);
    }

    @Transactional
    public void delete(Long commentId, UserResponseDto loginUser) {

        Comment comment = findByIdOrElseThrow(commentId);

        exceptionHandler.checkSameId(comment.getUser().getId(), loginUser.id(), "본인이 작성한 댓글이 아닙니다.");

        commentRepository.delete(comment);
    }

    public Long countByScheduleId(Long id) {
        return commentRepository.countByScheduleId(id);
    }

    public Comment findByIdOrElseThrow(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new CustomNotFoundException("존재하지 않는 댓글입니다."));
    }
}
