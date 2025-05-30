package com.itschool.study_pod.domain.chatRoom.dto.response;

import com.itschool.study_pod.domain.studygroup.entity.StudyGroup;
import com.itschool.study_pod.domain.user.dto.response.UserResponse;
import com.itschool.study_pod.domain.user.entity.User;
import com.itschool.study_pod.global.enumclass.ChatRoomType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Schema(description = "채팅방 응답 DTO")
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class ChatRoomResponse {
    private Long id;

    private ChatRoomType type;

    private UserResponse user1;

    private UserResponse user2;

    private StudyGroup studyGroup;

    private String name;

    protected String createdBy;

    protected LocalDateTime createdAt;

    protected String updatedBy;

    protected LocalDateTime updatedAt;

    protected boolean isDeleted;

    public static ChatRoomResponse withId(Long id) {
        return ChatRoomResponse.builder()
                .id(id)
                .build();
    }
}
