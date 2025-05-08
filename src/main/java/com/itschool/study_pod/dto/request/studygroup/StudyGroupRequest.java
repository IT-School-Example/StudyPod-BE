package com.itschool.study_pod.dto.request.studygroup;

import com.itschool.study_pod.dto.ReferenceDto;
import com.itschool.study_pod.embedable.WeeklySchedule;
import com.itschool.study_pod.enumclass.FeeType;
import com.itschool.study_pod.enumclass.MeetingMethod;
import com.itschool.study_pod.enumclass.RecruitmentStatus;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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

    @NotNull
    private Integer maxMembers;

    @NotNull
    private MeetingMethod meetingMethod; // Enum (ONLINE, OFFLINE, BOTH)

    @NotNull
    private RecruitmentStatus recruitmentStatus; // Enum (RECRUITING, CLOSED)

    private FeeType feeType; // Enum (MONTHLY, YEARLY, PER_EVENT, ONE_TIME)

    private Long amount;

    @NotNull
    private ReferenceDto leader;

    @NotNull
    private ReferenceDto address;

    @NotNull
    private ReferenceDto subjectArea;

    private Set<String> keywords;

    private Set<WeeklySchedule> weeklySchedules;

}
