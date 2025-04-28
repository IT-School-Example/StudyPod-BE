package com.itschool.study_pod.dto.request;

import com.itschool.study_pod.dto.request.address.SggRequest;
import com.itschool.study_pod.embedable.WeeklySchedule;
import com.itschool.study_pod.entity.address.Sgg;
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

    // fk용으로 필요
    private Long id;

    private String title;
    private String description;
    private Integer maxMembers;
    private MeetingMethod meetingMethod; // Enum (ONLINE, OFFLINE, BOTH)
    private RecruitmentStatus recruitmentStatus; // Enum (RECRUITING, CLOSED)
    private FeeType feeType; // Enum (MONTHLY, YEARLY, PER_EVENT, ONE_TIME)
    private Long amount;
    private UserRequest leader;
    private SggRequest address;
    private SubjectAreaRequest subjectArea;
    private Set<String> keywords;
    private Set<WeeklySchedule> weeklySchedules;

}
