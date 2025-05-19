package com.itschool.study_pod.domain.interestedsubject.dto.request;

import com.itschool.study_pod.global.base.dto.ReferenceDto;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data // 종합선물세트 : @Getter, @Setter, @ToString, @EqualsAndHashCode, @RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class InterestedSubjectRequest {

    @NotNull
    private ReferenceDto user;

    @NotNull
    private ReferenceDto subjectArea;
}
