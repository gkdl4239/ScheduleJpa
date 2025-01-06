package com.example.schedulejpa.controller;


import com.example.schedulejpa.common.Const;
import com.example.schedulejpa.dto.CommentRequestDto;
import com.example.schedulejpa.dto.CommentResponseDto;
import com.example.schedulejpa.dto.UpdateCommentRequestDto;
import com.example.schedulejpa.dto.UserDto;
import com.example.schedulejpa.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schedules")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/{scheduleId}/comments")
    public ResponseEntity<CommentResponseDto> save(
            @PathVariable Long scheduleId,
            @Validated @RequestBody CommentRequestDto requestDto,
            @SessionAttribute(name = Const.LOGIN_USER, required = false)
            UserDto loginUser) {


        CommentResponseDto responseDto = commentService.save(
                scheduleId,
                requestDto.contents(),
                loginUser);

        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @GetMapping("/{scheduleId}/comments")
    public ResponseEntity<List<CommentResponseDto>> findAll(
            @PathVariable Long scheduleId

    ) {
        List<CommentResponseDto> responseDtoList = commentService.findAll(scheduleId);

        return new ResponseEntity<>(responseDtoList, HttpStatus.OK);

    }

    @PatchMapping("/comments/{commentId}")
    public ResponseEntity<Void> update(
            @PathVariable Long commentId,
            @Validated @RequestBody UpdateCommentRequestDto requestDto,
            @SessionAttribute(name = Const.LOGIN_USER, required = false)
            UserDto loginUser
    ) {

        commentService.update(commentId, requestDto.contents(), loginUser);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<Void> delete(
            @PathVariable Long commentId,
            @SessionAttribute(name = Const.LOGIN_USER, required = false)
            UserDto loginUser
    ) {

        commentService.delete(commentId, loginUser);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
