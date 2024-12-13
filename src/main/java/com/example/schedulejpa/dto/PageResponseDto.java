package com.example.schedulejpa.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class PageResponseDto<T> {

    private final List<T> contents;
    private final int currentPage;
    private final int totalPages;

}
