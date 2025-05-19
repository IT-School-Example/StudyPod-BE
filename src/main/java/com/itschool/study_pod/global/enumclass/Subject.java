package com.itschool.study_pod.global.enumclass;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Subject {
    LANGUAGE("어학 관련 스터디"),
    IT("IT 및 프로그래밍 관련 스터디"),
    EXAM("자격증 취득 및 시험 준비 스터디"),
    JOB("취업 준비 및 경력 개발 스터디"),
    SCHOOL("학교 과목 관련 스터디"),
    HOBBY("취미 및 자기 계발 스터디"),
    CULTURE("문화 및 예술 관련 스터디"),
    SCIENCE("과학 관련 스터디"),
    HUMANITIES("인문학 관련 스터디"),
    BUSINESS("경영 및 경제 관련 스터디"),
    ETC("기타 분야의 스터디");

    private final String description;
}
