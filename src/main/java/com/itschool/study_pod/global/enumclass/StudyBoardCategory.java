package com.itschool.study_pod.global.enumclass;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum StudyBoardCategory {
    NOTICE("공지사항"),
    FREE("자유 게시판");

    private final String description;
}
