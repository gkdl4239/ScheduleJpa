package com.example.schedulejpa.service;

import com.example.schedulejpa.common.Const;
import com.example.schedulejpa.config.PasswordEncoder;
import com.example.schedulejpa.dto.SignUpResponseDto;
import com.example.schedulejpa.dto.UserResponseDto;
import com.example.schedulejpa.entity.User;
import com.example.schedulejpa.exception.CustomBadRequestException;
import com.example.schedulejpa.exception.CustomNotFoundException;
import com.example.schedulejpa.repository.UserRepository;
import com.example.schedulejpa.handler.ExceptionHandler;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ExceptionHandler exceptionHandler;

    @Transactional
    public SignUpResponseDto signUp(String username, String email, String password) {

        Optional<User> sameEmail = userRepository.findByEmail(email);

        if (sameEmail.isPresent()) {
            throw new CustomBadRequestException("이미 사용중인 이메일입니다.");
        }

        // 비밀번호 암호화 하여 DB에 저장
        String encodedPassword = passwordEncoder.encode(password);
        User user = new User(username, email, encodedPassword);

        User savedUser = userRepository.save(user);

        return SignUpResponseDto.toDto(savedUser);

    }

    @Transactional
    public void login(String email, String password, HttpServletRequest request) {

        Optional<User> user = userRepository.findByEmail(email);

        if (user.isEmpty()) {
            throw new CustomBadRequestException("아이디나 비밀번호가 일치하지 않습니다.");
        }

        User foundUser = user.get();

        exceptionHandler.checkSamePw(password, foundUser.getPassword(), "아이디나 비밀번호가 일치하지 않습니다.");

        // 이메일,비밀번호를 대조해 DB에 검색되면 세션에 저장
        HttpSession session = request.getSession();
        UserResponseDto loginUser = findById(foundUser.getId());
        session.setAttribute(Const.LOGIN_USER, loginUser);

    }


    public UserResponseDto findById(Long userId) {

        User foundUser = findByIdOrElseThrow(userId);

        return UserResponseDto.toDto(foundUser);
    }

    @Transactional
    public void update(Long id, UserResponseDto loginUser, String username, String oldPassword, String newPassword) {

        exceptionHandler.checkSameId(id, loginUser.id(), "본인의 계정이 아닙니다.");

        User foundUser = findByIdOrElseThrow(id);

        xorPassword(oldPassword, newPassword);

        // 기존,신규 비밀번호 입력시 기존 비밀번호 검사 후 변경
        if (oldPassword != null) {

            exceptionHandler.checkSamePw(oldPassword, foundUser.getPassword(), "기존 비밀번호가 일치하지 않습니다.");

            foundUser.setPassword(newPassword);
        }

        if (username != null) {
            foundUser.setUsername(username);
        }

    }

    // 기존 비밀번호와 신규 비밀번호 중 하나만 입력하면 오류
    private void xorPassword(String oldPassword, String newPassword) {
        if (!(oldPassword == null && newPassword == null) && (oldPassword == null || newPassword == null)) {
            throw new CustomBadRequestException("기존 비밀번호와 새 비밀번호를 둘다 입력해주세요.");
        }
    }

    @Transactional
    public void delete(Long id, UserResponseDto loginUser) {

        exceptionHandler.checkSameId(id, loginUser.id(), "본인의 계정이 아닙니다.");

        userRepository.deleteById(id);
    }

    public User findByIdOrElseThrow(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new CustomNotFoundException("유저를 찾을 수 없습니다."));
    }
}
