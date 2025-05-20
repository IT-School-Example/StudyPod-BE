package com.itschool.study_pod.global.enumclass;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum FeeType {
    MONTHLY("매월"),
    YEARLY("매년"),
    PER_EVENT("모임마다"),
    ONE_TIME("가입 시 1회");

    private final String description;
}
