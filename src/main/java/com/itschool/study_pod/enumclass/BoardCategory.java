package com.itschool.study_pod.enumclass;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum BoardCategory {
    NOTICE("공지사항"),
    QNA("자주하는 질문"),
    FREE("자유 게시판"),
    STUDY("스터디 내 게시판");

    private final String description;
}
