package com.example.schedulejpa.service;

import com.example.schedulejpa.common.Const;
import com.example.schedulejpa.config.PasswordEncoder;
import com.example.schedulejpa.dto.SignUpResponseDto;
import com.example.schedulejpa.dto.UserDto;
import com.example.schedulejpa.entity.User;
import com.example.schedulejpa.exception.CustomBadRequestException;
import com.example.schedulejpa.exception.CustomNotFoundException;
import com.example.schedulejpa.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public SignUpResponseDto signUp(String username, String email, String password) {

        if(userRepository.findByEmail(email).isPresent()) {
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

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomBadRequestException("아이디나 비밀번호가 일치하지 않습니다."));

        checkSamePw(password, user.getPassword(), "아이디나 비밀번호가 일치하지 않습니다.");

        // 이메일,비밀번호를 대조해 DB에 검색되면 세션에 저장
        HttpSession session = request.getSession();
        UserDto loginUser = findById(user.getId());
        session.setAttribute(Const.LOGIN_USER, loginUser);

    }


    public UserDto findById(Long userId) {

        User foundUser = findByIdOrElseThrow(userId);

        return UserDto.toDto(foundUser);
    }

    @Transactional
    public void update(Long id, UserDto loginUser, String username, String oldPassword, String newPassword) {

        User.isMine(id, loginUser.id(), "본인의 계정이 아닙니다.");

        User foundUser = findByIdOrElseThrow(id);

        xorPassword(oldPassword, newPassword);

        // 기존,신규 비밀번호 입력시 기존 비밀번호 검사 후 변경
        if (oldPassword != null) {

            checkSamePw(oldPassword, foundUser.getPassword(), "기존 비밀번호가 일치하지 않습니다.");

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
    public void delete(Long id, UserDto loginUser) {

        User.isMine(id, loginUser.id(), "본인의 계정이 아닙니다.");

        userRepository.deleteById(id);
    }

    public User findByIdOrElseThrow(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new CustomNotFoundException("유저를 찾을 수 없습니다."));
    }

    private void checkSamePw(String enteredPw, String foundPw, String warning) {
        if (!passwordEncoder.matches(enteredPw, foundPw)) {
            throw new CustomBadRequestException(warning);
        }
    }
}
