package com.example.schedulejpa.handler;

import com.example.schedulejpa.config.PasswordEncoder;
import com.example.schedulejpa.exception.CustomBadRequestException;
import com.example.schedulejpa.exception.CustomNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class ExceptionHandler {

    private final PasswordEncoder passwordEncoder;

    public void checkSameId(Long id1, Long id2, String warning) {
        if (!Objects.equals(id1, id2)) {
            throw new CustomBadRequestException(warning);
        }
    }

    public void checkSamePw(String enteredPw, String foundPw, String warning) {
        if (!passwordEncoder.matches(enteredPw, foundPw)) {
            throw new CustomBadRequestException(warning);
        }
    }
}
