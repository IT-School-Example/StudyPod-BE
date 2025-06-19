package com.itschool.study_pod.domain.chatRoom.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.itschool.study_pod.domain.ChatParticipant.entity.ChatParticipant;
import com.itschool.study_pod.domain.studygroup.entity.StudyGroup;
import com.itschool.study_pod.domain.user.entity.User;
import com.itschool.study_pod.global.base.dto.ReferenceDto;
import com.itschool.study_pod.global.enumclass.ChatRoomType;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Data // 종합선물세트 : @Getter, @Setter, @ToString, @EqualsAndHashCode, @RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class ChatRoomRequest {

    @NotNull
    private ChatRoomType type;

    @NotNull
    private String name;

    @NotNull
    private Long creatorId;

    @NotNull
    private ReferenceDto studyGroup;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Set<Long> memberIds = new HashSet<>();

}
