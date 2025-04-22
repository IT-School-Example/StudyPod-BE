package com.itschool.study_pod.entity;

import com.itschool.study_pod.entity.id.StudyGroupKeywordId;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "study_group_keywords")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudyGroupKeywords {

    @EmbeddedId
    private StudyGroupKeywordId id;

    @MapsId("studyGroupId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "study_group_id", nullable = false)
    private StudyGroups studyGroups;

    @Column(name = "keyword_name", insertable = false, updatable = false)
    private String keywordName;
}
