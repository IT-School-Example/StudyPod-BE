package com.itschool.study_pod.domain.ChatParticipant.dto.response;

import com.itschool.study_pod.domain.chatRoom.dto.response.ChatRoomResponse;
import com.itschool.study_pod.domain.chatRoom.entity.ChatRoom;
import com.itschool.study_pod.domain.user.dto.response.UserResponse;
import com.itschool.study_pod.domain.user.entity.User;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class ChatParticipantResponse {
    private Long id;

    private ChatRoomResponse chatRoom;

    private UserResponse user;

    private LocalDateTime joinedAt;

    protected String createdBy;

    protected LocalDateTime createdAt;

    protected String updatedBy;

    protected LocalDateTime updatedAt;

    protected boolean isDeleted;

    public static ChatParticipantResponse withId(Long id) {
        return ChatParticipantResponse.builder()
                .id(id)
                .build();
    }
}
