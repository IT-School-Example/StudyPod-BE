package com.itschool.study_pod.domain.studygroup.dto.response;

import com.itschool.study_pod.domain.studygroup.entity.StudyGroup;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Schema
@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class StudyGroupSummaryResponse {

    private Long id;

    private String title;

    public static StudyGroupSummaryResponse fromEntity(StudyGroup studyGroup) {
        return StudyGroupSummaryResponse.builder()
                .id(studyGroup.getId())
                .title(studyGroup.getTitle())
                .build();
    }

}


