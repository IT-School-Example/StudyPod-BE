package com.itschool.study_pod.domain.chatRoom.dto.request;

import com.itschool.study_pod.domain.studygroup.entity.StudyGroup;
import com.itschool.study_pod.domain.user.entity.User;
import com.itschool.study_pod.global.enumclass.ChatRoomType;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data // 종합선물세트 : @Getter, @Setter, @ToString, @EqualsAndHashCode, @RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class ChatRoomRequest {

    @NotNull
    private ChatRoomType type;

    private User user1;

    private User user2;

    private StudyGroup studyGroup;

    @NotNull
    private String name;
}
