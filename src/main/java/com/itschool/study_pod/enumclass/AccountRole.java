package com.itschool.study_pod.enumclass;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum AccountRole {
    ROLE_USER("일반 사용자"),
    ROLE_MODERATOR("ADMIN 계정 (운영 권한)"),
    ROLE_SUPER( "SUPER 계정 (모든 권한)");

    private final String description;
}
