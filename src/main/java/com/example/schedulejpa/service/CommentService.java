package com.example.schedulejpa.service;

import com.example.schedulejpa.dto.CommentResponseDto;
import com.example.schedulejpa.dto.UserResponseDto;
import com.example.schedulejpa.entity.Comment;
import com.example.schedulejpa.entity.Schedule;
import com.example.schedulejpa.entity.User;
import com.example.schedulejpa.repository.CommentRepository;
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
public class CommentService {

    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    public CommentResponseDto save(Long scheduleId, String contents, UserResponseDto loginUser) {

        User user = userRepository.findByIdOrElseThrow(loginUser.getId());
        Schedule schedule = scheduleRepository.findByIdOrElseThrow(scheduleId);
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

        return commentRepository.findByScheduleId(scheduleId).stream()
                .map(CommentResponseDto::toDto)
                .toList();

    }

    @Transactional
    public void update(Long scheduleId, Long commentId, String contents, UserResponseDto loginUser) {

        Comment comment = commentRepository.findByIdOrElseThrow(commentId);

        if(!Objects.equals(comment.getUser().getId(), loginUser.getId())){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "본인이 작성한 댓글이 아닙니다.");
        }
        comment.setContents(contents);
    }

    @Transactional
    public void delete(Long scheduleId, Long commentId, UserResponseDto loginUser) {

        Comment comment = commentRepository.findByIdOrElseThrow(commentId);

        if(!Objects.equals(comment.getUser().getId(), loginUser.getId())){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "본인이 작성한 댓글이 아닙니다.");
        }

        commentRepository.delete(comment);
    }
}
