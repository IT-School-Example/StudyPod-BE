package com.itschool.study_pod.domain.studygroup.dto.response;

import com.itschool.study_pod.domain.studygroup.entity.StudyGroup;
import com.itschool.study_pod.domain.subjectarea.dto.response.SubjectAreaResponse;
import com.itschool.study_pod.domain.user.dto.response.UserResponse;
import com.itschool.study_pod.global.address.dto.response.SidoResponse;
import com.itschool.study_pod.global.embedable.WeeklySchedule;
import com.itschool.study_pod.global.enumclass.FeeType;
import com.itschool.study_pod.global.enumclass.MeetingMethod;
import com.itschool.study_pod.global.enumclass.RecruitmentStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Schema(description = "스터디 그룹 응답 DTO")
@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class StudyGroupResponse {

    @Schema(description = "스터디 그룹 ID", example = "1")
    private Long id;

    @Schema(description = "스터디 제목", example = "백엔드 자바 스터디")
    private String title;

    @Schema(description = "스터디 설명", example = "스프링 부트를 배우는 스터디입니다.")
    private String description;

    @Schema(description = "최대 모집 인원", example = "5")
    private Integer maxMembers;

    @Schema(description = "모임 방식", example = "ONLINE")
    private MeetingMethod meetingMethod;

    @Schema(description = "모집 상태", example = "RECRUITING")
    private RecruitmentStatus recruitmentStatus;

    @Schema(description = "회비 유형", example = "MONTHLY")
    private FeeType feeType;

    @Schema(description = "회비 금액", example = "10000")
    private Long amount;

    @Schema(description = "스터디 리더 정보")
    private UserResponse leader;

    @Schema(description = "시도")
    private SidoResponse sido;

    @Schema(description = "주제 영역")
    private SubjectAreaResponse subjectArea;

    @Schema(description = "스터디 키워드 목록", example = "[\"자바\", \"스프링\"]")
    private Set<String> keywords;

    @Schema(description = "주간 스케줄 정보")
    private Set<WeeklySchedule> weeklySchedules;

    @Schema(description = "생성자", example = "admin")
    private String createdBy;

    @Schema(description = "생성 일시", example = "2025-05-08T15:47:00")
    private LocalDateTime createdAt;

    @Schema(description = "수정자", example = "admin")
    private String updatedBy;

    @Schema(description = "수정 일시", example = "2025-05-08T16:10:00")
    private LocalDateTime updatedAt;

    private String fileUrl;

    public static StudyGroupResponse withId(Long id) {
        return StudyGroupResponse.builder()
                .id(id)
                .build();
    }

    // ✅ 정적 팩토리 메서드 추가
    public static StudyGroupResponse fromEntity(StudyGroup group) {
        return StudyGroupResponse.builder()
                .id(group.getId())
                .title(group.getTitle())
                .description(group.getDescription())
                .build();
    }
}
