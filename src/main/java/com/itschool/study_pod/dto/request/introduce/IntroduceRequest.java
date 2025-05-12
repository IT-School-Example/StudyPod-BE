package com.itschool.study_pod.dto.request.introduce;

import com.itschool.study_pod.dto.request.studygroup.StudyGroupRequest;
import lombok.*;

@Data // 종합선물세트 : @Getter, @Setter, @ToString, @EqualsAndHashCode, @RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class IntroduceRequest {
    private Long id;

    private String content;

    private boolean isPosted;

    private StudyGroupRequest studyGroup;
}
