package com.itschool.study_pod.entity;

import com.itschool.study_pod.dto.request.InterestedSubject.InterestedSubjectRequest;
import com.itschool.study_pod.dto.request.StudyGroup.StudyGroupRequest;
import com.itschool.study_pod.dto.response.InterestedSubjectResponse;
import com.itschool.study_pod.entity.base.BaseEntity;
import com.itschool.study_pod.ifs.Convertible;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "interested_subjects")
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

    // 욫청 DTO -> Entity로 변환하는 메서드
    public static InterestedSubject of(InterestedSubjectRequest request, User user, SubjectArea subjectArea) { // create용
        return InterestedSubject.builder()
                .user(user)
                .subjectArea(subjectArea)
                .build();
    }

    @Override
    public void update(InterestedSubjectRequest request) {
        this.subjectArea = request.getSubjectArea();
    }

    @Override
    public InterestedSubjectResponse response() {
        return InterestedSubjectResponse.builder()
                .id(this.id)
                .user(this.user.response())
                .subjectArea(this.subjectArea.response())
                .build();
//        return null;
    }
}
