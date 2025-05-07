package com.itschool.study_pod.dto.request;

import com.itschool.study_pod.enumclass.EnrollmentStatus;
import lombok.*;

import java.time.LocalDateTime;

@Data // 종합선물세트 : @Getter, @Setter, @ToString, @EqualsAndHashCode, @RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class EnrollmentRequest {

    // fk용으로 필요
    private Long id;

    // 시스템 시간 : LocalDateTime.now()로 계산
    private LocalDateTime appliedAt;

    private String introduce;

    private LocalDateTime joinedAt;

    private EnrollmentStatus status;

    private StudyGroupRequest studyGroup;

    private UserRequest user;
}
