package com.itschool.study_pod.domain.ChatParticipant.dto.request;

import com.itschool.study_pod.domain.chatRoom.entity.ChatRoom;
import com.itschool.study_pod.domain.user.entity.User;
import com.itschool.study_pod.global.base.dto.ReferenceDto;
import jakarta.validation.constraints.NotNull;
import lombok.*;


import java.time.LocalDateTime;

@Data // 종합선물세트 : @Getter, @Setter, @ToString, @EqualsAndHashCode, @RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class ChatParticipantRequest {

    @NotNull
    private ReferenceDto chatRoom;

    @NotNull
    private ReferenceDto user;

    @NotNull
    private LocalDateTime joinedAt;
}
