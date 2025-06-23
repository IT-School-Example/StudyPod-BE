package com.itschool.study_pod.domain.chatRoom.dto.request;

import com.itschool.study_pod.global.base.dto.ReferenceDto;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data // 종합선물세트 : @Getter, @Setter, @ToString, @EqualsAndHashCode, @RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@SuperBuilder
public class StudyGroupChatRoomRequest extends ChatRoomRequest {

    @NotNull
    private ReferenceDto studyGroup;
}
