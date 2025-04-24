package com.itschool.study_pod.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiResponse<T> {
    // api 통신 시간
    private LocalDateTime transactionTime;

    // api 응답 코드
    private String resultCode;

    // api 부가 설명
    private String description;

    private T data;

    private Pagination pagination;

    // Status : OK
    public static <T> ApiResponse<T> OK() {
        return (ApiResponse <T>)ApiResponse.builder()
                .transactionTime(LocalDateTime.now())
                .resultCode("OK")
                .description("OK")
                .build();
    }

    // Data : OK
    public static <T> ApiResponse<T> OK(T data) {
        return (ApiResponse <T>)ApiResponse.builder()
                .transactionTime(LocalDateTime.now())
                .resultCode("OK")
                .description("OK")
                .data(data)
                .build();
    }

    // Data : OK
    public static <T> ApiResponse<T> OK(T data, Pagination pagination) {
        return (ApiResponse <T>)ApiResponse.builder()
                .transactionTime(LocalDateTime.now())
                .resultCode("OK")
                .description("OK")
                .data(data)
                .pagination(pagination)
                .build();
    }

    // ERROR
    public static <T> ApiResponse<T> ERROR(String description) {
        return (ApiResponse <T>)ApiResponse.builder()
                .transactionTime(LocalDateTime.now())
                .resultCode("ERROR")
                .description(description)
                .build();
    }
}


