package com.itschool.study_pod.domain.studygroup.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.itschool.study_pod.global.base.dto.ReferenceDto;
import com.itschool.study_pod.global.embedable.WeeklySchedule;
import com.itschool.study_pod.global.enumclass.FeeType;
import com.itschool.study_pod.global.enumclass.MeetingMethod;
import com.itschool.study_pod.global.enumclass.RecruitmentStatus;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudyGroupRequest {

    @NotBlank(message = "제목은 필수입니다.")
    private String title;

    @NotBlank(message = "소개는 필수입니다.")
    private String description;

    @NotNull(message = "최대 인원은 필수입니다.")
    @Min(value = 2, message = "스터디는 최소 2명 이상이어야 합니다.")
    private Integer maxMembers;

    @NotNull(message = "스터디 방식은 필수입니다.")
    private MeetingMethod meetingMethod;

    @NotNull(message = "모집 상태는 필수입니다.")
    private RecruitmentStatus recruitmentStatus;

    private FeeType feeType;

    private Long amount;

    @NotNull(message = "리더 정보는 필수입니다.")
    private ReferenceDto leader;

    @NotNull(message = "주소 정보는 필수입니다.")
    private ReferenceDto address;

    @NotNull(message = "주제 영역은 필수입니다.")
    private ReferenceDto subjectArea;

    @NotEmpty(message = "키워드는 최소 1개 이상 입력해야 합니다.")
    private Set<String> keywords;

    @NotEmpty(message = "주간 일정은 최소 1개 이상 필요합니다.")
    private Set<WeeklySchedule> weeklySchedules;

    @JsonFormat(pattern = "HH:mm")
    private LocalTime startTime;
}
