package com.itschool.study_pod.global.enumclass;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum MessageType {
    ENTER("채팅방 입장 메시지"),
    TALK("일반 채팅 메시지"),
    LEAVE("채팅방 퇴장 메시지");

    private final String description;
}
