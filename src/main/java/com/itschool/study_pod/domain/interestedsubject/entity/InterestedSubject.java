package com.itschool.study_pod.domain.interestedsubject.entity;

import com.itschool.study_pod.domain.interestedsubject.dto.request.InterestedSubjectRequest;
import com.itschool.study_pod.domain.interestedsubject.dto.response.InterestedSubjectResponse;
import com.itschool.study_pod.domain.subjectarea.entity.SubjectArea;
import com.itschool.study_pod.domain.user.entity.User;
import com.itschool.study_pod.domain.subjectarea.dto.response.SubjectAreaResponse;
import com.itschool.study_pod.domain.user.dto.response.UserResponse;
import com.itschool.study_pod.global.base.BaseEntity;
import com.itschool.study_pod.global.base.crud.Convertible;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Table(name = "interested_subjects",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"account_id", "subject_area_id"})
})
@SQLDelete(sql = "UPDATE interested_subjects SET is_deleted = true WHERE interested_subject_id = ?")
@Where(clause = "is_deleted = false")
public class InterestedSubject extends BaseEntity implements Convertible<InterestedSubjectRequest, InterestedSubjectResponse> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "interested_subject_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_area_id", nullable = false)
    private SubjectArea subjectArea;

    // 요청청 DTO -> Entity로 변환하는 메서드
    public static InterestedSubject of(InterestedSubjectRequest request) { // create용
        return InterestedSubject.builder()
                .user(User.withId(request.getUser().getId()))
                .subjectArea(SubjectArea.withId(request.getSubjectArea().getId()))
                .build();
    }

    @Override
    public void update(InterestedSubjectRequest request) {
        throw new IllegalArgumentException("해당 연결 테이블에서 업데이트는 허용하지 않습니다. 삭제 후 다시 생성하세요");
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
