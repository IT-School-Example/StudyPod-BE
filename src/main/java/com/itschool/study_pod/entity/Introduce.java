package com.itschool.study_pod.entity;

import com.itschool.study_pod.dto.request.IntroduceRequest;
import com.itschool.study_pod.dto.response.IntroduceResponse;
import com.itschool.study_pod.entity.base.BaseEntity;
import com.itschool.study_pod.ifs.Convertible;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "introduces")
public class Introduce extends BaseEntity implements Convertible<IntroduceRequest, IntroduceResponse> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "introduce_id")
    private Long id;

    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "study_group_id", nullable = false)
    private StudyGroup studyGroup;

    @Column(nullable = false)
    protected boolean isPosted;

    public static Introduce of(IntroduceRequest request) { // create용
        return Introduce.builder()
                .id(request.getId())
                .content(request.getContent())
                .studyGroup(StudyGroup.of(request.getStudyGroup()))
                .isPosted(request.isPosted())
                .build();
    }

    @Override
    public void update(IntroduceRequest request) {
        this.content = request.getContent();
        this.isPosted = request.isPosted();
    }

    @Override
    public IntroduceResponse response() {
        return IntroduceResponse.builder()
                .id(this.id)
                .content(this.content)
                .isPosted(this.isPosted)
                .studyGroup(this.studyGroup.response())
                .isDeleted(this.isDeleted)
                .createdAt(this.createdAt)
                .createdBy(this.createdBy)
                .updatedAt(this.updatedAt)
                .updatedBy(this.updatedBy)
                .build();

    }
}
