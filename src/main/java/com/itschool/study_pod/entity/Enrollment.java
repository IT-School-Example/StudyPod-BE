package com.itschool.study_pod.entity;

import com.itschool.study_pod.dto.request.enrollment.EnrollmentRequest;
import com.itschool.study_pod.dto.response.EnrollmentResponse;
import com.itschool.study_pod.dto.response.StudyGroupResponse;
import com.itschool.study_pod.dto.response.UserResponse;
import com.itschool.study_pod.entity.base.BaseEntity;
import com.itschool.study_pod.enumclass.EnrollmentStatus;
import com.itschool.study_pod.ifs.Convertible;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "enrollments")
@SQLDelete(sql = "UPDATE enrollments SET is_deleted = true WHERE enrollment_id = ?")
@Where(clause = "is_deleted = false")
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
        if(request != null) {
            return Enrollment.builder()
                    .appliedAt(LocalDateTime.now())
                    .introduce(request.getIntroduce())
                    .status(EnrollmentStatus.PENDING)
                    .user(User.withId(request.getUser().getId()))
                    .studyGroup(StudyGroup.withId(request.getStudyGroup().getId()))
                    .build();
        }
        return null;
    }

    @Override
    public void update(EnrollmentRequest request) {
        this.appliedAt = request.getAppliedAt();
        this.introduce = request.getIntroduce();
        this.joinedAt = request.getJoinedAt();
        this.status = request.getStatus();
    }

    public void memberKick() {
        this.status = EnrollmentStatus.BANNED;
    }

    @Override
    public EnrollmentResponse response() {
        return EnrollmentResponse.builder()
                .id(this.id)
                .appliedAt(this.appliedAt)
                .introduce(this.introduce)
                .joinedAt(this.joinedAt)
                .status(this.status)
                .user(UserResponse.withId(this.user.getId()))
                .studyGroup(StudyGroupResponse.withId(this.studyGroup.getId()))
                .createdAt(this.createdAt)
                .createdBy(this.createdBy)
                .updatedAt(this.updatedAt)
                .updatedBy(this.updatedBy)
                .build();
    }

    public static Enrollment withId(Long id) {
        return Enrollment.builder()
                .id(id)
                .build();
    }
}
