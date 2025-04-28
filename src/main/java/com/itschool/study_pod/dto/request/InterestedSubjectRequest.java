package com.itschool.study_pod.dto.request;

import lombok.*;

@Data // 종합선물세트 : @Getter, @Setter, @ToString, @EqualsAndHashCode, @RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class InterestedSubjectRequest {

    // fk용으로 필요
    private Long id;

    private UserRequest user;

    private SubjectAreaRequest subjectArea;
}
