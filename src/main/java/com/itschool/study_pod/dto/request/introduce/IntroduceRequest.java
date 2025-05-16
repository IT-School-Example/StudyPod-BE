package com.itschool.study_pod.dto.request.introduce;

import com.itschool.study_pod.dto.ReferenceDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data // 종합선물세트 : @Getter, @Setter, @ToString, @EqualsAndHashCode, @RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class IntroduceRequest {

    @NotBlank(message = "소개는 필수입니다.")
    private String content;

    @NotBlank(message = "소개는 여부를 선택해야 합니다.")
    private boolean isPosted;

    @NotNull(message = "스터디 그룹 정보는 필수입니다.")
    private ReferenceDto studyGroup;
}
