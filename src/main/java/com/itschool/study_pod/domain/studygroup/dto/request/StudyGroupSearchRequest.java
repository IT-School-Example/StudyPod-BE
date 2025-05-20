package com.itschool.study_pod.domain.studygroup.dto.request;

import com.itschool.study_pod.global.base.dto.ReferenceDto;
import com.itschool.study_pod.global.enumclass.MeetingMethod;
import com.itschool.study_pod.global.enumclass.RecruitmentStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "스터디 그룹 필터 검색 요청 DTO")
public class StudyGroupSearchRequest {

    @Schema(description = "스터디 방식", example = "ONLINE", allowableValues = {"ONLINE", "OFFLINE", "BOTH"})
    private MeetingMethod meetingMethod;

    @Schema(description = "모집 상태", example = "RECRUITING", allowableValues = {"RECRUITING", "CLOSED"})
    private RecruitmentStatus recruitmentStatus;

    @Schema(description = "주제 영역 ID 객체")
    private ReferenceDto subjectArea;
}
