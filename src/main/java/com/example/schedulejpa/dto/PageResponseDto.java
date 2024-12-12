package com.example.schedulejpa.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class PageResponseDto<T> {

    private List<T> contents;
    private int currentPage;
    private int totalPages;

}
