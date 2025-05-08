package com.itschool.study_pod.entity;

import com.itschool.study_pod.dto.request.InterestedSubjectRequest;
import com.itschool.study_pod.dto.response.InterestedStudyResponse;
import com.itschool.study_pod.dto.response.InterestedSubjectResponse;
import com.itschool.study_pod.dto.response.SubjectAreaResponse;
import com.itschool.study_pod.dto.response.UserResponse;
import com.itschool.study_pod.entity.base.BaseEntity;
import com.itschool.study_pod.ifs.Convertible;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "interested_subjects")
@SQLDelete(sql = "UPDATE interested_subjects SET is_deleted = true WHERE interested_subject_id = ?")
@Where(clause = "is_deleted = false")
public class InterestedSubject extends BaseEntity implements Convertible<InterestedSubjectRequest, InterestedSubjectResponse> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "interested_subject_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_area_id", nullable = false)
    private SubjectArea subjectArea;

    // 요청청 DTO -> Entity로 변환하는 메서드
    public static InterestedSubject of(InterestedSubjectRequest request) { // create용
        if(request != null) {
            return InterestedSubject.builder()
                    .user(User.withId(request.getUser().getId()))
                    .subjectArea(SubjectArea.withId(request.getSubjectArea().getId()))
                    .build();
        }
        return null;
    }

    @Override
    public void update(InterestedSubjectRequest request) {
        throw new IllegalArgumentException("연결 테이블에서 업데이트는 허용하지 않습니다.");
        // this.subjectArea = SubjectArea.of(request.getSubjectArea());
    }

    @Override
    public InterestedSubjectResponse response() {
        return InterestedSubjectResponse.builder()
                .id(this.id)
                .user(UserResponse.withId(this.user.getId()))
                .subjectArea(SubjectAreaResponse.withId(this.subjectArea.getId()))
                .createdAt(this.createdAt)
                .createdBy(this.createdBy)
                .updatedAt(this.updatedAt)
                .updatedBy(this.updatedBy)
                .build();
    }

    public static InterestedSubject withId(Long id) {
        return InterestedSubject.builder()
                .id(id)
                .build();
    }

}
