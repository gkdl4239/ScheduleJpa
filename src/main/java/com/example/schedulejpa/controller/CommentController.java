package com.example.schedulejpa.controller;


import com.example.schedulejpa.common.Const;
import com.example.schedulejpa.dto.CommentRequestDto;
import com.example.schedulejpa.dto.CommentResponseDto;
import com.example.schedulejpa.dto.UserResponseDto;
import com.example.schedulejpa.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schedules/{scheduleId}/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentResponseDto> save(
            @PathVariable Long scheduleId,
            @RequestBody CommentRequestDto requestDto,
            @SessionAttribute(name = Const.LOGIN_USER, required = false)
            UserResponseDto loginUser) {


        CommentResponseDto responseDto = commentService.save(
                scheduleId,
                requestDto.getContents(),
                loginUser);

        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CommentResponseDto>> findAll(
            @PathVariable Long scheduleId

    ) {
        List<CommentResponseDto> responseDtoList = commentService.findAll(scheduleId);

        return new ResponseEntity<>(responseDtoList,HttpStatus.OK);

    }

    @PatchMapping("/{commentId}")
    public ResponseEntity<Void> update(
            @PathVariable Long scheduleId,
            @PathVariable Long commentId,
            @RequestBody CommentRequestDto requestDto,
            @SessionAttribute(name = Const.LOGIN_USER, required = false)
            UserResponseDto loginUser
    ) {

        commentService.update(scheduleId, commentId, requestDto.getContents(), loginUser);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> delete(
            @PathVariable Long scheduleId,
            @PathVariable Long commentId,
            @SessionAttribute(name = Const.LOGIN_USER, required = false)
            UserResponseDto loginUser
    ) {

        commentService.delete(scheduleId, commentId, loginUser);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
