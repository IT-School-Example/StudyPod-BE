package com.itschool.study_pod.dto.request.studygroup;

import com.itschool.study_pod.dto.ReferenceDto;
import com.itschool.study_pod.embedable.WeeklySchedule;
import com.itschool.study_pod.enumclass.FeeType;
import com.itschool.study_pod.enumclass.MeetingMethod;
import com.itschool.study_pod.enumclass.RecruitmentStatus;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class StudyGroupRequest {

    @NotEmpty
    private String title;

    @NotEmpty
    private String description;

    @NotEmpty
    private Integer maxMembers;

    @NotEmpty
    private MeetingMethod meetingMethod; // Enum (ONLINE, OFFLINE, BOTH)

    @NotEmpty
    private RecruitmentStatus recruitmentStatus; // Enum (RECRUITING, CLOSED)

    private FeeType feeType; // Enum (MONTHLY, YEARLY, PER_EVENT, ONE_TIME)

    private Long amount;

    @NotEmpty
    private ReferenceDto leader;

    @NotEmpty
    private ReferenceDto address;

    @NotEmpty
    private ReferenceDto subjectArea;

    private Set<String> keywords;

    private Set<WeeklySchedule> weeklySchedules;

}
