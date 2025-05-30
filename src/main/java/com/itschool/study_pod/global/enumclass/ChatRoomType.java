package com.itschool.study_pod.global.enumclass;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ChatRoomType {
    DIRECT("1:1채팅"),
    GROUP("그룹채팅");

    private final String description;
}

