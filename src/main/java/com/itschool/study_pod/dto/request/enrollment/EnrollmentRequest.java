package com.itschool.study_pod.dto.request.enrollment;

import com.itschool.study_pod.dto.ReferenceDto;
import com.itschool.study_pod.enumclass.EnrollmentStatus;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.time.LocalDateTime;

@Data // 종합선물세트 : @Getter, @Setter, @ToString, @EqualsAndHashCode, @RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class EnrollmentRequest {

    // 시스템 시간 : LocalDateTime.now()로 계산
    @NotEmpty
    private LocalDateTime appliedAt;

    @NotEmpty
    private String introduce;

    private LocalDateTime joinedAt;

    @NotEmpty
    private EnrollmentStatus status;

    @NotEmpty
    private ReferenceDto studyGroup;

    @NotEmpty
    private ReferenceDto user;
}
