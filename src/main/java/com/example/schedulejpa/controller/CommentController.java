package com.example.schedulejpa.controller;


import com.example.schedulejpa.common.Const;
import com.example.schedulejpa.dto.CommentRequestDto;
import com.example.schedulejpa.dto.CommentResponseDto;
import com.example.schedulejpa.dto.UserResponseDto;
import com.example.schedulejpa.service.CommentService;
import lombok.RequiredArgsConstructor;
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
}
