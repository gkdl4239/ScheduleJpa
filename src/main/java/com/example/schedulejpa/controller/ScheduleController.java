package com.example.schedulejpa.controller;

import com.example.schedulejpa.common.Const;
import com.example.schedulejpa.dto.*;
import com.example.schedulejpa.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/schedules")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping
    public ResponseEntity<ScheduleResponseDto> save(@Validated @RequestBody ScheduleRequestDto requestDto,
                                                    @SessionAttribute(name = Const.LOGIN_USER, required = false)
                                                    UserResponseDto loginUser) {

        // 세션을 이용하여 작성자 정보 저장
        ScheduleResponseDto scheduleResponseDto = scheduleService.save(loginUser.getId(), requestDto.getTitle(), requestDto.getContents());

        return new ResponseEntity<>(scheduleResponseDto, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> findById(@PathVariable Long id) {

        ScheduleResponseDto scheduleResponseDto = scheduleService.findById(id);

        return new ResponseEntity<>(scheduleResponseDto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<PageResponseDto<ScheduleResponseDto>> findAll(
            @RequestParam(defaultValue = "1") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize
    ) {

        // 페이징과 내림차순 정렬을 위한 선언
        Sort sort = Sort.by(Sort.Direction.DESC, "updatedAt");
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, sort);
        PageResponseDto<ScheduleResponseDto> response = scheduleService.findAll(pageable);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateTitleAndContents(
            @PathVariable Long id,
            @Validated @RequestBody UpdateScheduleRequestDto requestDto,
            @SessionAttribute(name = Const.LOGIN_USER, required = false)
            UserResponseDto loginUser) {

        scheduleService.update(id, requestDto.getTitle(), requestDto.getContents(), loginUser);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id,
            @SessionAttribute(name = Const.LOGIN_USER, required = false)
            UserResponseDto loginUser) {

        scheduleService.delete(id, loginUser);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
