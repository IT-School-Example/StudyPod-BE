package com.itschool.study_pod.dto.response;

import com.itschool.study_pod.enumclass.Subject;
import lombok.*;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class SubjectAreaResponse {
    private Long id;

    private Subject subject;
}
