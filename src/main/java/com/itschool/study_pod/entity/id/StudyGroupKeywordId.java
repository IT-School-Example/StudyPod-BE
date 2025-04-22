package com.itschool.study_pod.entity.id;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class StudyGroupKeywordId implements Serializable {

    @Column(name = "study_group_id")
    private Long studyGroupId;

    @Column(name = "keyword_name")
    private String keywordName;
}
