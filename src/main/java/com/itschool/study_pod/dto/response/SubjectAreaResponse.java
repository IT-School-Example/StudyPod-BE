package com.itschool.study_pod.dto.response;

import com.itschool.study_pod.enumclass.Subject;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class SubjectAreaResponse {
    private Long id;

    private Subject subject;
}
