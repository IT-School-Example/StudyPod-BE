package com.itschool.study_pod.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@Getter
@NoArgsConstructor
public class StudyGroupResponse {
    private Long id;
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
    private Set<WeeklyScheduleResponse> weeklySchedules;

    @Builder
    public StudyGroupResponse(Long id, String title, String description, Integer maxMembers, String meetingMethod,
                              String recruitmentStatus, String feeType, Long amount, Long leaderId, Long addressId,
                              Long subjectAreaId, Set<String> keywords, Set<WeeklyScheduleResponse> weeklySchedules) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.maxMembers = maxMembers;
        this.meetingMethod = meetingMethod;
        this.recruitmentStatus = recruitmentStatus;
        this.feeType = feeType;
        this.amount = amount;
        this.leaderId = leaderId;
        this.addressId = addressId;
        this.subjectAreaId = subjectAreaId;
        this.keywords = keywords;
        this.weeklySchedules = weeklySchedules;
    }

    @Getter
    @NoArgsConstructor
    public static class WeeklyScheduleResponse {
        private String dayOfWeek;
        private String startTime; // "HH:mm"
        private String endTime;   // "HH:mm"

        @Builder
        public WeeklyScheduleResponse(String dayOfWeek, String startTime, String endTime) {
            this.dayOfWeek = dayOfWeek;
            this.startTime = startTime;
            this.endTime = endTime;
        }
    }
}
