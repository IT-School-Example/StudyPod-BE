package com.itschool.study_pod.enumclass;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum BoardCategory {
    NOTICE("공지사항, 관리자만"),
    FAQ("자주 묻는 질문, 관리자만"),
    FREE("스터디 내 자유 게시판, 스터디 멤버"),
    STUDY("스터디 내 공지사항, 스터디장");

    private final String description;
}
