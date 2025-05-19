package com.itschool.study_pod.domain.studygroup.dto.request;

import com.itschool.study_pod.global.base.dto.ReferenceDto;
import com.itschool.study_pod.global.enumclass.MeetingMethod;
import com.itschool.study_pod.global.enumclass.RecruitmentStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class StudyGroupSearchRequest {

    private MeetingMethod meetingMethod; // Enum (ONLINE, OFFLINE, BOTH)

    private RecruitmentStatus recruitmentStatus; // Enum (RECRUITING, CLOSED)

    private ReferenceDto subjectArea;

}
