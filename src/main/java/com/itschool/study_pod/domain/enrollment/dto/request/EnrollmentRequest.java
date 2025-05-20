package com.itschool.study_pod.domain.enrollment.dto.request;

import com.itschool.study_pod.global.base.dto.ReferenceDto;
import com.itschool.study_pod.global.enumclass.EnrollmentStatus;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data // 종합선물세트 : @Getter, @Setter, @ToString, @EqualsAndHashCode, @RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class EnrollmentRequest {

    // 시스템 시간 : LocalDateTime.now()로 계산
    /*@Schema(hidden = true)
    private LocalDateTime appliedAt;*/

    @NotEmpty
    private String introduce;

    /*@Schema(hidden = true)
    private LocalDateTime joinedAt;*/

    private EnrollmentStatus status;

    @NotNull
    private ReferenceDto studyGroup;

    @NotNull
    private ReferenceDto user;
}
