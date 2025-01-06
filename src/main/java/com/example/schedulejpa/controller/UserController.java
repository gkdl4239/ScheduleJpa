package com.example.schedulejpa.controller;

import com.example.schedulejpa.common.Const;
import com.example.schedulejpa.dto.SignUpRequestDto;
import com.example.schedulejpa.dto.SignUpResponseDto;
import com.example.schedulejpa.dto.UpdateUserRequestDto;
import com.example.schedulejpa.dto.UserDto;
import com.example.schedulejpa.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<SignUpResponseDto> singUp(@Validated @RequestBody SignUpRequestDto requestDto) {

        SignUpResponseDto ResponseDto = userService.signUp(
                requestDto.username(),
                requestDto.email(),
                requestDto.password());

        return new ResponseEntity<>(ResponseDto, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> findById(@PathVariable Long id) {
        UserDto userDto = userService.findById(id);

        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> update(
            @PathVariable Long id,
            @Validated @RequestBody UpdateUserRequestDto requestDto,
            @SessionAttribute(name = Const.LOGIN_USER, required = false)
            UserDto loginUser) {

        userService.update(id, loginUser, requestDto.username(), requestDto.oldPassword(), requestDto.newPassword());

        return new ResponseEntity<>(HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(
            @PathVariable Long id,
            @SessionAttribute(name = Const.LOGIN_USER, required = false)
            UserDto loginUser) {

        userService.delete(id, loginUser);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
