package com.itschool.study_pod.entity;

import com.itschool.study_pod.dto.request.Enrollment.EnrollmentRequest;
import com.itschool.study_pod.dto.request.InterestedSubject.InterestedSubjectRequest;
import com.itschool.study_pod.dto.response.EnrollmentResponse;
import com.itschool.study_pod.entity.base.BaseEntity;
import com.itschool.study_pod.enumclass.EnrollmentStatus;
import com.itschool.study_pod.ifs.Convertible;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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
                .build();
    }

    @Override
    public void update(EnrollmentRequest request) {

    }

    @Override
    public EnrollmentResponse response() {
        return null;
    }
}
