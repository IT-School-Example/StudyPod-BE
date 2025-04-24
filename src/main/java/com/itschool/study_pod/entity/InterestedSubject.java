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

    public static InterestedSubject of(InterestedSubjectRequest request) { // create용
        return InterestedSubject.builder()
                .build();
    }

    @Override
    public void update(InterestedSubjectRequest request) {

    }

    @Override
    public InterestedSubjectResponse response() {
        return null;
    }
}
