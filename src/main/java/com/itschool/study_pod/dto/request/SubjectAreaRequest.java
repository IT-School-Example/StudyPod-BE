package com.itschool.study_pod.dto.request;

import com.itschool.study_pod.enumclass.Subject;
import lombok.*;

@Data // 종합선물세트 : @Getter, @Setter, @ToString, @EqualsAndHashCode, @RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
// 요청!
public class SubjectAreaRequest {
    // fk용으로 필요
    private Long id;

    private Subject subject;
}
