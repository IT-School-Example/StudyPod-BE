package com.itschool.study_pod.dto.request.InterestedSubject;

import com.itschool.study_pod.dto.request.User.UserRequest;
import com.itschool.study_pod.entity.SubjectArea;
import lombok.*;

@Data // 종합선물세트 : @Getter, @Setter, @ToString, @EqualsAndHashCode, @RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
// 요청!
public class InterestedSubjectRequest {

    private Long userId;

    private Long subjectArea;
}
