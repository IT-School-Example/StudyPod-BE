package com.itschool.study_pod.global.enumclass;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum AccountRole {
    ROLE_USER("일반 사용자"),
    ROLE_ADMIN("관리자");

    private final String description;
}
