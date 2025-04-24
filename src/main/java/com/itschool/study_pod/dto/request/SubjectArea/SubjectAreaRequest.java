package com.itschool.study_pod.dto.request.SubjectArea;

import com.itschool.study_pod.enumclass.Subject;
import lombok.*;

@Data // 종합선물세트 : @Getter, @Setter, @ToString, @EqualsAndHashCode, @RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class SubjectAreaRequest {

    private Subject subject;

}
