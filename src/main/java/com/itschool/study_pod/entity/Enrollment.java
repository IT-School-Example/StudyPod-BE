package com.itschool.study_pod.entity;

import com.itschool.study_pod.dto.request.EnrollmentRequest;
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

    public static Enrollment of(EnrollmentRequest request) { // create용
        return Enrollment.builder()
                .appliedAt(request.getAppliedAt())
                .introduce(request.getIntroduce())
                .status(request.getStatus())
                .user(User.of(request.getUser()))
                .studyGroup(StudyGroup.of(request.getStudyGroup()))
                .build();
    }

    @Override
    public void update(EnrollmentRequest request) {
        this.appliedAt = request.getAppliedAt();
        this.introduce = request.getIntroduce();
        this.joinedAt = request.getJoinedAt();
        this.status = request.getStatus();
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
                .isDeleted(this.isDeleted)
                .createdAt(this.createdAt)
                .createdBy(this.createdBy)
                .updatedAt(this.updatedAt)
                .updatedBy(this.updatedBy)
                .build();
    }

}
