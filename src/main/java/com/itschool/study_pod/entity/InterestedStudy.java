package com.itschool.study_pod.entity;

import com.itschool.study_pod.dto.request.InterestedStudyRequest;
import com.itschool.study_pod.dto.response.InterestedStudyResponse;
import com.itschool.study_pod.dto.response.StudyGroupResponse;
import com.itschool.study_pod.dto.response.UserResponse;
import com.itschool.study_pod.entity.base.BaseEntity;
import com.itschool.study_pod.ifs.Convertible;
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
@Table(name = "interested_studies")
@SQLDelete(sql = "UPDATE interested_studies SET is_deleted = true WHERE interested_study_id = ?")
@Where(clause = "is_deleted = false")
public class InterestedStudy extends BaseEntity implements Convertible<InterestedStudyRequest, InterestedStudyResponse> {
    // 관심 목록(북마크 같은 역할)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "interested_study_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "study_group_id", nullable = false)
    private StudyGroup studyGroup;

    // 요청 DTO -> Entity로 변환하는 메서드
    public static InterestedStudy of(InterestedStudyRequest request) { // create용
        if (request != null) {
            return InterestedStudy.builder()
                    .id(request.getId())
                    .user(User.of(request.getUser()))
                    .studyGroup(StudyGroup.of(request.getStudyGroup()))
                    .build();
        }
        return null;
    }

    @Override
    public void update(InterestedStudyRequest request) {
        throw new IllegalArgumentException("연결 테이블에서 업데이트는 허용하지 않습니다.");
    }
    @Override
    public InterestedStudyResponse response() {
        return InterestedStudyResponse.builder()
                .id(this.id)
                .user(UserResponse.builder()
                        .id(this.user.getId())
                        .build())
                .studyGroup(StudyGroupResponse.builder()
                        .id(this.studyGroup.getId())
                        .build())
                .createdAt(this.createdAt)
                .createdBy(this.createdBy)
                .updatedAt(this.updatedAt)
                .updatedBy(this.updatedBy)
                .build();
    }
}

