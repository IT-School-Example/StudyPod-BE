package com.itschool.study_pod.domain.introduce.dto.response;

import com.itschool.study_pod.domain.studygroup.dto.response.StudyGroupResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class IntroduceResponse {

    @Schema(description = "스터디 소개 ID", example = "1")
    private Long id;

    @Schema(description = "소개 내용 ID", example = "파이썬 초보들도 많이 와 주세요.")
    private String content;

    @Schema(description = "공개 여부")
    private boolean isPosted;

    @Schema(description = "스터디 그룹 정보")
    private StudyGroupResponse studyGroup;

    @Schema(description = "생성자", example = "admin")
    private String createdBy;

    @Schema(description = "생성 일시", example = "2025-05-08T15:47:00")
    private LocalDateTime createdAt;

    @Schema(description = "수정자", example = "admin")
    private String updatedBy;

    @Schema(description = "수정 일시", example = "2025-05-08T16:10:00")
    private LocalDateTime updatedAt;

    public static IntroduceResponse withId(Long id) {
        return IntroduceResponse.builder()
                .id(id)
                .build();
    }
}
