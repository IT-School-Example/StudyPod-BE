package com.itschool.study_pod.enumclass;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum EnrollmentStatus {
    PENDING("대기 중"),
    APPROVED("승인"),
    REJECTED("거절");

    private final String description;
}
