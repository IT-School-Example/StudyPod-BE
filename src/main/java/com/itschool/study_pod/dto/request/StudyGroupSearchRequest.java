package com.itschool.study_pod.dto.request;

import com.itschool.study_pod.dto.ReferenceDto;
import com.itschool.study_pod.dto.request.address.SggRequest;
import com.itschool.study_pod.embedable.WeeklySchedule;
import com.itschool.study_pod.enumclass.FeeType;
import com.itschool.study_pod.enumclass.MeetingMethod;
import com.itschool.study_pod.enumclass.RecruitmentStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class StudyGroupSearchRequest {

    private MeetingMethod meetingMethod; // Enum (ONLINE, OFFLINE, BOTH)

    private RecruitmentStatus recruitmentStatus; // Enum (RECRUITING, CLOSED)

    private ReferenceDto subjectArea;

}
