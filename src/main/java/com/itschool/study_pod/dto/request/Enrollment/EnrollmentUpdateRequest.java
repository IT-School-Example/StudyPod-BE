package com.itschool.study_pod.dto.request.Enrollment;

import com.itschool.study_pod.enumclass.EnrollmentStatus;
import lombok.*;

import java.time.LocalDateTime;

@Data // 종합선물세트 : @Getter, @Setter, @ToString, @EqualsAndHashCode, @RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class EnrollmentUpdateRequest extends EnrollmentRequest {

    private LocalDateTime appliedAt;

    private String introduce;

    private LocalDateTime joinedAt;

    private EnrollmentStatus status;
}
