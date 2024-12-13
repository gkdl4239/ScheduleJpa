package com.example.schedulejpa.service;

import com.example.schedulejpa.config.PasswordEncoder;
import com.example.schedulejpa.dto.LoginResponseDto;
import com.example.schedulejpa.dto.SignUpResponseDto;
import com.example.schedulejpa.dto.UserResponseDto;
import com.example.schedulejpa.entity.User;
import com.example.schedulejpa.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public SignUpResponseDto signUp(String username, String email, String password) {

        Optional<User> sameEmail = userRepository.findByEmail(email);

        if(sameEmail.isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이미 사용중인 이메일입니다.");
        }

        String encodedPassword = passwordEncoder.encode(password);
        User user = new User(username, email, encodedPassword);

        User savedUser = userRepository.save(user);

        return new SignUpResponseDto(savedUser.getId(), savedUser.getUsername(), savedUser.getEmail());

    }

    public LoginResponseDto login(String email, String password) {

        Optional<User> user = userRepository.findByEmail(email);

        if(user.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "존재하지 않는 아이디 입니다.");
        }

        User userFound = user.get();

        if(!passwordEncoder.matches(password, userFound.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다.");
        }


        return new LoginResponseDto(userFound.getId());
    }

    public UserResponseDto findById(Long userId) {

        User findUser = userRepository.findByIdOrElseThrow(userId);


        return new UserResponseDto(findUser.getId(), findUser.getUsername(), findUser.getEmail());
    }

    public void delete(Long id, UserResponseDto loginUser) {

        if(!Objects.equals(id, loginUser.getId())){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "본인의 계정이 아닙니다.");
        }

        userRepository.deleteById(id);
    }
}
