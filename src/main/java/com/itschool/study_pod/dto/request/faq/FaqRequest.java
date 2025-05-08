package com.itschool.study_pod.dto.request.faq;

import com.itschool.study_pod.dto.ReferenceDto;
import lombok.*;

@Data // 종합선물세트 : @Getter, @Setter, @ToString, @EqualsAndHashCode, @RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class FaqRequest {

    private String question;

    private String answer;

    private Boolean visible;

    private ReferenceDto admin;
}
