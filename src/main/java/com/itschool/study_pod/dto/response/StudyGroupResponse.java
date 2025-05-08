package com.itschool.study_pod.dto.response;

import com.itschool.study_pod.dto.response.address.SggResponse;
import com.itschool.study_pod.embedable.WeeklySchedule;
import com.itschool.study_pod.entity.SubjectArea;
import com.itschool.study_pod.enumclass.FeeType;
import com.itschool.study_pod.enumclass.MeetingMethod;
import com.itschool.study_pod.enumclass.RecruitmentStatus;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class StudyGroupResponse {

    private Long id;
    private String title;
    private String description;
    private Integer maxMembers;
    private MeetingMethod meetingMethod; // Enum (ONLINE, OFFLINE, BOTH)
    private RecruitmentStatus recruitmentStatus; // Enum (RECRUITING, CLOSED)
    private FeeType feeType; // Enum (MONTHLY, YEARLY, PER_EVENT, ONE_TIME)
    private Long amount;
    private UserResponse leader;
    private SggResponse address;
    private SubjectAreaResponse subjectArea;
    private Set<String> keywords;
    private Set<WeeklySchedule> weeklySchedules;


    private String createdBy;

    private LocalDateTime createdAt;

    private String updatedBy;

    private LocalDateTime updatedAt;

    public static StudyGroupResponse withId(Long id) {
        return StudyGroupResponse.builder()
                .id(id)
                .build();
    }
}
