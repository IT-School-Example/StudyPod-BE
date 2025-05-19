package com.itschool.study_pod.domain.subjectarea.dto.request;

import com.itschool.study_pod.global.enumclass.Subject;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data // 종합선물세트 : @Getter, @Setter, @ToString, @EqualsAndHashCode, @RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class SubjectAreaRequest {
    @NotNull
    private Subject subject;
}
