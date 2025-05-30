package com.itschool.study_pod.domain.Message.dto.response;

import com.itschool.study_pod.domain.chatRoom.entity.ChatRoom;
import com.itschool.study_pod.domain.user.dto.response.UserResponse;
import com.itschool.study_pod.domain.user.entity.User;
import com.itschool.study_pod.global.enumclass.MessageType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Schema(description = "채팅방 응답 DTO")
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class MessageResponse {

    private Long id;

    private ChatRoom chatRoom;

    private UserResponse sender;

    private String messageText;

    private boolean isRead;

    private MessageType messageType;

    protected String createdBy;

    protected LocalDateTime createdAt;

    protected String updatedBy;

    protected LocalDateTime updatedAt;

    protected boolean isDeleted;

    public static MessageResponse withId(Long id) {
        return MessageResponse.builder()
                .id(id)
                .build();
    }
}
