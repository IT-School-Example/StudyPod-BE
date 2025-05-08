package com.itschool.study_pod.dto.request.faq;

import com.itschool.study_pod.dto.ReferenceDto;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Data // 종합선물세트 : @Getter, @Setter, @ToString, @EqualsAndHashCode, @RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class FaqRequest {

    @NotEmpty
    private String question;

    @NotEmpty
    private String answer;

    @NotEmpty
    private Boolean visible;

    @NotEmpty
    private ReferenceDto admin;
}
