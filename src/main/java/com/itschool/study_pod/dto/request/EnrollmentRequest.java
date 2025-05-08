package com.itschool.study_pod.dto.request;

import com.itschool.study_pod.dto.ReferenceDto;
import com.itschool.study_pod.enumclass.EnrollmentStatus;
import lombok.*;

import java.time.LocalDateTime;

@Data // 종합선물세트 : @Getter, @Setter, @ToString, @EqualsAndHashCode, @RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class EnrollmentRequest {

    // 시스템 시간 : LocalDateTime.now()로 계산
    private LocalDateTime appliedAt;

    private String introduce;

    private LocalDateTime joinedAt;

    private EnrollmentStatus status;

    private ReferenceDto studyGroup;

    private ReferenceDto user;
}
