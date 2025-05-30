package com.itschool.study_pod.global.enumclass;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum MessageType {
    ENTER("채팅방 입장 메시지"),
    TALK("일반 채팅 메시지"),
    LEAVE("채팅방 퇴장 메시지");

    private final String description;

    @JsonCreator
    public static MessageType from(String value) {
        // Enum 이름 혹은 설명값(description) 둘 다 허용
        return Arrays.stream(MessageType.values())
                .filter(e -> e.name().equalsIgnoreCase(value) || e.getDescription().equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("알 수 없는 메시지 타입입니다."));
    }

    @JsonValue
    public String toValue() { return this.name(); }
}
