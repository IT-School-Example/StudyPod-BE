package com.itschool.study_pod.domain.Message.dto.request;

import com.itschool.study_pod.domain.Message.dto.response.MessageResponse;
import com.itschool.study_pod.domain.Message.entity.Message;
import com.itschool.study_pod.domain.chatRoom.entity.ChatRoom;
import com.itschool.study_pod.domain.user.dto.request.UserRequest;
import com.itschool.study_pod.domain.user.entity.User;
import com.itschool.study_pod.global.enumclass.MessageType;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data // 종합선물세트 : @Getter, @Setter, @ToString, @EqualsAndHashCode, @RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class MessageRequest {

    @NotNull
    private ChatRoom chatRoom;

    // 메시지를 보낸 사용자
    @NotNull
    private UserRequest sender;

    // 메시지를 받는 사용자
    @NotNull
    private UserRequest receiver;

    // 메시지 내용
    @NotNull
    private String messageText;

    // 메시지 읽었는지 여부
    @NotNull
    private boolean isRead;

    @NotNull
    private MessageType messageType;

}
