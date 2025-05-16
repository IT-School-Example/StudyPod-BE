package com.itschool.study_pod.dto.response;

import com.itschool.study_pod.enumclass.Subject;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
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
