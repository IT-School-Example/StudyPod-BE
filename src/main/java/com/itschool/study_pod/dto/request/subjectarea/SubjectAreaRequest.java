package com.itschool.study_pod.dto.request.subjectarea;

import com.itschool.study_pod.enumclass.Subject;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Data // 종합선물세트 : @Getter, @Setter, @ToString, @EqualsAndHashCode, @RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class SubjectAreaRequest {

    @NotEmpty
    private Subject subject;
}
