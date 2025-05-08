package com.itschool.study_pod.dto.request.studygroup;

import com.itschool.study_pod.dto.ReferenceDto;
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
public class StudyGroupRequest {

    private String title;

    private String description;

    private Integer maxMembers;

    private MeetingMethod meetingMethod; // Enum (ONLINE, OFFLINE, BOTH)

    private RecruitmentStatus recruitmentStatus; // Enum (RECRUITING, CLOSED)

    private FeeType feeType; // Enum (MONTHLY, YEARLY, PER_EVENT, ONE_TIME)

    private Long amount;

    private ReferenceDto leader;

    private ReferenceDto address;

    private ReferenceDto subjectArea;

    private Set<String> keywords;

    private Set<WeeklySchedule> weeklySchedules;

}
