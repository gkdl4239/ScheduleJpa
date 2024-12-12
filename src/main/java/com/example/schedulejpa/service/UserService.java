package com.example.schedulejpa.service;

import com.example.schedulejpa.dto.LoginResponseDto;
import com.example.schedulejpa.dto.SignUpResponseDto;
import com.example.schedulejpa.dto.UserResponseDto;
import com.example.schedulejpa.entity.User;
import com.example.schedulejpa.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
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

         return new UserResponseDto(findUser.getUsername(), findUser.getEmail());
    }

    public SignUpResponseDto signUp(String username, String email, String password) {

        User user = new User(username, email, password);

        User savedUser = userRepository.save(user);

        return new SignUpResponseDto(savedUser.getId(), savedUser.getUsername(), savedUser.getEmail());

    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
}
