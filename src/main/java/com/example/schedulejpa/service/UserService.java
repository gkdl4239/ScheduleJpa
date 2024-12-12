package com.example.schedulejpa.service;

import com.example.schedulejpa.dto.LoginResponseDto;
import com.example.schedulejpa.dto.UserResponseDto;
import com.example.schedulejpa.entity.User;
import com.example.schedulejpa.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public LoginResponseDto login(String email, String password) {
        Long findId = userRepository.findIdByEmailAndPassword(email, password)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED,"이메일과 비밀번호가 일치하지 않습니다."));

        return new LoginResponseDto(findId);
    }

    public UserResponseDto findById(Long userId) {
         User findUser = userRepository.findById(userId)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 유저입니다"));

         return new UserResponseDto(findUser.getEmail(),findUser.)
    }
}
