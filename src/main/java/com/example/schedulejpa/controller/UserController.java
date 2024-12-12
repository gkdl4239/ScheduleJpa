package com.example.schedulejpa.controller;

import com.example.schedulejpa.dto.SignUpRequestDto;
import com.example.schedulejpa.dto.SignUpResponseDto;
import com.example.schedulejpa.dto.UserResponseDto;
import com.example.schedulejpa.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<SignUpResponseDto> singUp(@RequestBody SignUpRequestDto requestDto){

        SignUpResponseDto ResponseDto = userService.signUp(
                requestDto.getUsername(),
                requestDto.getEmail(),
                requestDto.getPassword());

        return new ResponseEntity<>(ResponseDto, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> findById(@PathVariable Long id) {
        UserResponseDto userResponseDto = userService.findById(id);

        return new ResponseEntity<>(userResponseDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        userService.deleteById(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
