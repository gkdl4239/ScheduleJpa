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
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class SessionUserController {

    private final UserService userService;

    @PostMapping("/login")
    public String login(
            @Valid @RequestBody LoginRequestDto requestDto,
            HttpServletRequest request
    ) {

        LoginResponseDto responseDto = userService.login(requestDto.getEmail(),requestDto.getPassword());
        Long userId = responseDto.getId();

        HttpSession session = request.getSession();

        UserResponseDto loginUser = userService.findById(userId);
        session.setAttribute(Const.LOGIN_USER,loginUser);

        return "로그인이 완료되었습니다.";
    }

    @PostMapping("/logout")
    public String logout(
            HttpServletRequest request
    ) {


        HttpSession session = request.getSession(false);
        if(session != null) {
            session.invalidate();
        }

        return "로그아웃이 완료되었습니다.";
    }
}
