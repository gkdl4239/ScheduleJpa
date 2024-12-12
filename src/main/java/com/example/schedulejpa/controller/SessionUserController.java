package com.example.schedulejpa.controller;


import com.example.schedulejpa.common.Const;
import com.example.schedulejpa.dto.LoginRequestDto;
import com.example.schedulejpa.dto.LoginResponseDto;
import com.example.schedulejpa.dto.UserResponseDto;
import com.example.schedulejpa.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping
@RestController
@RequiredArgsConstructor
public class SessionUserController {

    private UserService userService;

    @PostMapping("/login")
    public String login(
            @Valid @ModelAttribute LoginRequestDto requestDto,
            HttpServletRequest request
    ) {

        LoginResponseDto responseDto = userService.login(requestDto.getEmail(),requestDto.getPassword());
        Long userId = responseDto.getId();

        HttpSession session = request.getSession();

        UserResponseDto loginUser = userService.findById(userId);
        session.setAttribute(Const.LOGIN_USER,loginUser);

    }
}
