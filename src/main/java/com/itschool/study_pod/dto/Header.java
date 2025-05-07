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
public class Header<T> {

    private LocalDateTime transactionTime; // 통신 시간

    private String resultCode; // OK, ERROR

    private String description; // ERROR 메시지

    private T data; // OK 데이터

    private Pagination pagination; // 페이지 정보

    private String searchStr;

    // OK (빈 데이터)
    public static <T> Header<T> OK() {
        return OK(null, null);
    }

    // OK (데이터만)
    public static <T> Header<T> OK(T data) {
        return OK(data, null);
    }

    // OK (데이터 + 페이징)
    public static <T> Header<T> OK(T data, Pagination pagination) {
        return buildResponse("OK", "OK", data, pagination);
    }

    // ERROR (에러 설명만)
    public static <T> Header<T> ERROR(String description) {
        return buildResponse("ERROR", description, null, null);
    }

    // 공통 빌더
    private static <T> Header<T> buildResponse(String resultCode, String description, T data, Pagination pagination) {
        return Header.<T>builder()
                .transactionTime(LocalDateTime.now())
                .resultCode(resultCode)
                .description(description)
                .data(data)
                .pagination(pagination)
                .build();
    }
}