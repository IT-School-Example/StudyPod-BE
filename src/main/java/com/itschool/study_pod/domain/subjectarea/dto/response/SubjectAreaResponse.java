package com.itschool.study_pod.domain.subjectarea.dto.response;

import com.itschool.study_pod.global.enumclass.Subject;
import lombok.*;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class SubjectAreaResponse {
    private Long id;

    private Subject subject;

    public static SubjectAreaResponse withId(Long id) {
        return SubjectAreaResponse.builder()
                .id(id)
                .build();
    }
}
