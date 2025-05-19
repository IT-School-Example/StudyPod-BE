package com.itschool.study_pod.domain.interestedstudy.entity;

import com.itschool.study_pod.domain.interestedstudy.dto.request.InterestedStudyRequest;
import com.itschool.study_pod.domain.interestedstudy.dto.response.InterestedStudyResponse;
import com.itschool.study_pod.domain.studygroup.entity.StudyGroup;
import com.itschool.study_pod.domain.user.entity.User;
import com.itschool.study_pod.domain.studygroup.dto.response.StudyGroupResponse;
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
@AllArgsConstructor
@Builder
@Table(name = "interested_studies",
        uniqueConstraints = {
        @UniqueConstraint(columnNames = {"account_id", "study_group_id"})
})
@SQLDelete(sql = "UPDATE interested_studies SET is_deleted = true WHERE interested_study_id = ?")
@Where(clause = "is_deleted = false")
public class InterestedStudy extends BaseEntity implements Convertible<InterestedStudyRequest, InterestedStudyResponse> {
    // 관심 목록(북마크 같은 역할)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "interested_study_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "study_group_id", nullable = false)
    private StudyGroup studyGroup;

    // 요청 DTO -> Entity로 변환하는 메서드
    public static InterestedStudy of(InterestedStudyRequest request) { // create용
        return InterestedStudy.builder()
                .user(User.withId(request.getUser().getId()))
                .studyGroup(StudyGroup.withId(request.getStudyGroup().getId()))
                .build();
    }

    @Override
    public void update(InterestedStudyRequest request) {
        throw new IllegalArgumentException("해당 연결 테이블에서 업데이트는 허용하지 않습니다. 삭제 후 다시 생성하세요");
    }
    @Override
    public InterestedStudyResponse response() {
        return InterestedStudyResponse.builder()
                .id(this.id)
                .user(UserResponse.withId(this.user.getId()))
                .studyGroup(StudyGroupResponse.withId(this.studyGroup.getId()))
                .createdAt(this.createdAt)
                .createdBy(this.createdBy)
                .updatedAt(this.updatedAt)
                .updatedBy(this.updatedBy)
                .build();
    }

    public static InterestedStudy withId(Long id) {
        return InterestedStudy.builder()
                .id(id)
                .build();
    }
}

