package com.itschool.study_pod.entity;

import com.itschool.study_pod.dto.request.Enrollment.EnrollmentCreateRequest;
import com.itschool.study_pod.dto.request.Enrollment.EnrollmentRequest;
import com.itschool.study_pod.dto.request.Enrollment.EnrollmentUpdateRequest;
import com.itschool.study_pod.dto.response.EnrollmentResponse;
import com.itschool.study_pod.entity.base.BaseEntity;
import com.itschool.study_pod.enumclass.EnrollmentStatus;
import com.itschool.study_pod.ifs.Convertible;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "enrollments")
public class Enrollment extends BaseEntity implements Convertible<EnrollmentRequest, EnrollmentResponse> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "enrollment_id")
    private Long id;

    @Column(nullable = false)
    private LocalDateTime appliedAt;

    @Column(nullable = false)
    private String introduce;

    private LocalDateTime joinedAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EnrollmentStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "study_group_id", nullable = false)
    private StudyGroup studyGroup;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public static Enrollment of(EnrollmentCreateRequest request, User user, StudyGroup studyGroup) { // create용
        return Enrollment.builder()
                .appliedAt(request.getAppliedAt())
                .introduce(request.getIntroduce())
                .status(request.getStatus())
                .user(user)
                .studyGroup(studyGroup)
                .build();
    }

    @Override
    public void update(EnrollmentRequest request) {
        if(request instanceof EnrollmentUpdateRequest updateRequest) {
            this.appliedAt = updateRequest.getAppliedAt();
            this.introduce = updateRequest.getIntroduce();
            this.joinedAt = updateRequest.getJoinedAt();
            this.status = updateRequest.getStatus();
        } else {
            throw new IllegalArgumentException("지원하지 않는 요청 타입입니다: " + request.getClass().getSimpleName());
        }
    }

    @Override
    public EnrollmentResponse response() {
        return EnrollmentResponse.builder()
                .id(this.id)
                .appliedAt(this.appliedAt)
                .introduce(this.introduce)
                .joinedAt(this.joinedAt)
                .status(this.status)
                .user(this.user.response())
                .studyGroup(this.studyGroup.response())
                .build();
    }

}
