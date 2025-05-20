package com.itschool.study_pod.domain.interestedstudy.dto.request;

import com.itschool.study_pod.global.base.dto.ReferenceDto;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data // 종합선물세트 : @Getter, @Setter, @ToString, @EqualsAndHashCode, @RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class InterestedStudyRequest {

    @NotNull
    private ReferenceDto user;

    @NotNull
    private ReferenceDto studyGroup;

}
