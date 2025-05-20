package com.itschool.study_pod.global.enumclass;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum RecruitmentStatus {
    RECRUITING("모집 중"),
    CLOSED("모집 마감");

    private final String description;

    @JsonCreator
    public static RecruitmentStatus from(String value) {
        // Enum 이름 혹은 설명값(description) 둘 다 허용
        return Arrays.stream(RecruitmentStatus.values())
                .filter(e -> e.name().equalsIgnoreCase(value) || e.getDescription().equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("모집 상태 값이 유효하지 않음: " + value));
    }

    @JsonValue
    public String toValue() {
        return this.name();
    }
}
