package com.itschool.study_pod.dto.request.StudyGroup;

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
    private String meetingMethod; // Enum (ONLINE, OFFLINE, BOTH)
    private String recruitmentStatus; // Enum (RECRUITING, CLOSED)
    private String feeType; // Enum (MONTHLY, YEARLY, PER_EVENT, ONE_TIME)
    private Long amount;
    private Long leaderId;
    private Long addressId;
    private Long subjectAreaId;
    private Set<String> keywords;
    private Set<WeeklyScheduleRequest> weeklySchedules;

    @Getter
    @Setter
    @NoArgsConstructor
    public static class WeeklyScheduleRequest {
        private String dayOfWeek; // "MONDAY", "TUESDAY" 등
        private String startTime; // "HH:mm" 포맷
        private String endTime;   // "HH:mm" 포맷
    }
}
