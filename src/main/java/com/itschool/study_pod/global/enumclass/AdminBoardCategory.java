package com.itschool.study_pod.global.enumclass;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum AdminBoardCategory {
    NOTICE("공지사항"),
    FAQ("자주 묻는 질문");

    private final String description;
}
