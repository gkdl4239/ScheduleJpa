package com.example.schedulejpa.service;

import com.example.schedulejpa.dto.CommentResponseDto;
import com.example.schedulejpa.dto.UserResponseDto;
import com.example.schedulejpa.entity.Comment;
import com.example.schedulejpa.entity.Schedule;
import com.example.schedulejpa.entity.User;
import com.example.schedulejpa.repository.CommentRepository;
import com.example.schedulejpa.repository.ScheduleRepository;
import com.example.schedulejpa.repository.UserRepository;
import com.example.schedulejpa.handler.ExceptionHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final ExceptionHandler exceptionHandler;
    private final ScheduleService scheduleService;
    private final UserService userService;

    public CommentResponseDto save(Long scheduleId, String contents, UserResponseDto loginUser) {

        User user = userService.findByIdOrElseThrow(loginUser.getId());
        Schedule schedule = scheduleService.findByIdOrElseThrow(scheduleId);
        Comment comment = new Comment(contents, user, schedule);

        Comment savedComment = commentRepository.save(comment);

        return new CommentResponseDto(
                savedComment.getId(),
                savedComment.getUser().getUsername(),
                savedComment.getContents(),
                savedComment.getCreatedAt(),
                savedComment.getUpdatedAt()
        );

    }

    public List<CommentResponseDto> findAll(Long scheduleId) {

        scheduleService.findByIdOrElseThrow(scheduleId);

        return commentRepository.findByScheduleId(scheduleId, Sort.by(Sort.Direction.DESC, "updatedAt")).stream()
                .map(CommentResponseDto::toDto)
                .toList();

    }

    @Transactional
    public void update(Long commentId, String contents, UserResponseDto loginUser) {

        Comment comment = commentRepository.findByIdOrElseThrow(commentId);

        exceptionHandler.checkSameId(comment.getUser().getId(), loginUser.getId(), "본인이 작성한 댓글이 아닙니다.");

        comment.setContents(contents);
    }

    @Transactional
    public void delete(Long commentId, UserResponseDto loginUser) {

        Comment comment = commentRepository.findByIdOrElseThrow(commentId);

        exceptionHandler.checkSameId(comment.getUser().getId(), loginUser.getId(),"본인이 작성한 댓글이 아닙니다.");

        commentRepository.delete(comment);
    }

    public Long countByScheduleId(Long id) {
        return commentRepository.countByScheduleId(id);
    }
}
