package com.itschool.study_pod.dto.request.enrollment;

import com.itschool.study_pod.dto.ReferenceDto;
import com.itschool.study_pod.enumclass.EnrollmentStatus;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Data // 종합선물세트 : @Getter, @Setter, @ToString, @EqualsAndHashCode, @RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class EnrollmentRequest {

    // 시스템 시간 : LocalDateTime.now()로 계산
    @NotNull
    private LocalDateTime appliedAt;

    @NotEmpty
    private String introduce;

    private LocalDateTime joinedAt;

    @NotNull
    private EnrollmentStatus status;

    @NotNull
    private ReferenceDto studyGroup;

    @NotNull
    private ReferenceDto user;
}
