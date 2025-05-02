package com.itschool.study_pod.entity;

import com.itschool.study_pod.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
// 컬럼 작성 보류
public class InterestedStudy extends BaseEntity {
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

    // 요청
}

