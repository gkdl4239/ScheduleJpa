package com.example.schedulejpa.controller;

import com.example.schedulejpa.common.Const;
import com.example.schedulejpa.dto.ScheduleRequestDto;
import com.example.schedulejpa.dto.ScheduleResponseDto;
import com.example.schedulejpa.dto.UserResponseDto;
import com.example.schedulejpa.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schedules")
@RequiredArgsConstructor
public class ScheduleController {
    private final ScheduleService scheduleService;

    @PostMapping
    public ResponseEntity<ScheduleResponseDto> save(@RequestBody ScheduleRequestDto requestDto,
                                                    @SessionAttribute(name = Const.LOGIN_USER, required = false)
                                                    UserResponseDto loginUser) {

        ScheduleResponseDto scheduleResponseDto = scheduleService.save(loginUser.getId(), requestDto.getTitle(), requestDto.getContents());

        return new ResponseEntity<>(scheduleResponseDto, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> findById(@PathVariable Long id) {

        ScheduleResponseDto scheduleResponseDto = scheduleService.findById(id);

        return new ResponseEntity<>(scheduleResponseDto,HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ScheduleResponseDto>> findAll() {
        List<ScheduleResponseDto> scheduleResponseDtoList = scheduleService.findAll();

        return new ResponseEntity<>(scheduleResponseDtoList, HttpStatus.OK);
    }


    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateTitleAndContents(
            @PathVariable Long id,
            @RequestBody ScheduleRequestDto requestDto,
            @SessionAttribute(name = Const.LOGIN_USER, required = false)
            UserResponseDto loginUser) {

        scheduleService.update(id,requestDto.getTitle(), requestDto.getContents(), loginUser);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id,
            @SessionAttribute(name = Const.LOGIN_USER, required = false)
            UserResponseDto loginUser ) {

        scheduleService.delete(id,loginUser);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
