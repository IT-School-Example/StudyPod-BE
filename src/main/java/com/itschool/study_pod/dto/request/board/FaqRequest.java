package com.itschool.study_pod.dto.request.board;

import com.itschool.study_pod.dto.ReferenceDto;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data // 종합선물세트 : @Getter, @Setter, @ToString, @EqualsAndHashCode, @RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class FaqRequest {

    @NotEmpty
    private String title;

    @NotEmpty
    private String content;

    @NotNull
    private Boolean visible;

    @NotNull
    private ReferenceDto admin;
}
