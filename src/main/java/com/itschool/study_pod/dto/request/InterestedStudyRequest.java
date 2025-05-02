package com.itschool.study_pod.dto.request;

import com.itschool.study_pod.entity.StudyGroup;
import lombok.*;

@Data // 종합선물세트 : @Getter, @Setter, @ToString, @EqualsAndHashCode, @RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class InterestedStudyRequest {
    // fk용으로 필요
    private Long id;

    private UserRequest user;

    private StudyGroupRequest studyGroup;
}
