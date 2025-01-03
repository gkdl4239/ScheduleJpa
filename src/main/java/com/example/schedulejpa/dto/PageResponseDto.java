package com.example.schedulejpa.dto;

import lombok.Getter;

import java.util.List;

@Getter
public record PageResponseDto<T>(
        List<T> contents,
        int currentPage,
        int totalPages) {

}
