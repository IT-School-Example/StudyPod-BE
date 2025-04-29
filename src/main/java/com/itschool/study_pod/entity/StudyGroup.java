package com.itschool.study_pod.entity;

import com.itschool.study_pod.dto.request.StudyGroupRequest;
import com.itschool.study_pod.dto.response.StudyGroupResponse;
import com.itschool.study_pod.dto.response.SubjectAreaResponse;
import com.itschool.study_pod.dto.response.UserResponse;
import com.itschool.study_pod.dto.response.address.SggResponse;
import com.itschool.study_pod.embedable.WeeklySchedule;
import com.itschool.study_pod.entity.address.Sgg;
import com.itschool.study_pod.entity.base.BaseEntity;
import com.itschool.study_pod.enumclass.FeeType;
import com.itschool.study_pod.enumclass.MeetingMethod;
import com.itschool.study_pod.enumclass.RecruitmentStatus;
import com.itschool.study_pod.ifs.Convertible;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "study_groups")
public class StudyGroup extends BaseEntity implements Convertible<StudyGroupRequest, StudyGroupResponse> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "study_group_id")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(length = 255)
    private String description;

    @Column(nullable = false)
    private Integer maxMembers;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MeetingMethod meetingMethod;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RecruitmentStatus recruitmentStatus;

    @Enumerated(EnumType.STRING)
    private FeeType feeType;

    private Long amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "leader_id", referencedColumnName = "user_id")
    private User leader;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id", referencedColumnName = "sgg_id")
    private Sgg address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_area_id", nullable = false)
    private SubjectArea subjectArea;

    @Builder.Default
    @ElementCollection
    @CollectionTable(name = "study_group_keywords",
            joinColumns = @JoinColumn(name = "study_group_id"))
    @Column(name = "keyword_name", nullable = false)
    private Set<String> keywords = new HashSet<>();

    @Builder.Default
    @ElementCollection
    @CollectionTable(name = "study_group_weekly_schedules",
            joinColumns = @JoinColumn(name = "study_group_id"))
    @AttributeOverrides({
            @AttributeOverride(name = "dayOfWeek", column = @Column(name = "day_of_week")),
            @AttributeOverride(name = "timeRange.startTime", column = @Column(name = "start_time")),
            @AttributeOverride(name = "timeRange.endTime", column = @Column(name = "end_time"))
    })
    private Set<WeeklySchedule> weeklySchedules = new HashSet<>();

    public static StudyGroup of(StudyGroupRequest request) { // create용
        if(request != null) {
            return StudyGroup.builder()
                    .id(request.getId())
                    .title(request.getTitle())
                    .description(request.getDescription())
                    .maxMembers(request.getMaxMembers())
                    .meetingMethod(request.getMeetingMethod())
                    .recruitmentStatus(request.getRecruitmentStatus())
                    .feeType(request.getFeeType())
                    .amount(request.getAmount())
                    .leader(User.of(request.getLeader()))
                    .address(Sgg.of(request.getAddress()))
                    .subjectArea(SubjectArea.of(request.getSubjectArea()))
                    .keywords(request.getKeywords())
                    .weeklySchedules(request.getWeeklySchedules())
                    .build();
        }
        return null;
    }

    @Override
    public void update(StudyGroupRequest request) {
        this.title = request.getTitle();
        this.description = request.getDescription();
        this.maxMembers = request.getMaxMembers();
        this.meetingMethod = request.getMeetingMethod();
        this.recruitmentStatus = request.getRecruitmentStatus();
        this.feeType = request.getFeeType();
        this.amount = request.getAmount();
        // this.leader = User.of(request.getLeader());
        this.address = Sgg.of(request.getAddress());
        this.subjectArea = SubjectArea.of(request.getSubjectArea());
        this.keywords = request.getKeywords();
        this.weeklySchedules = request.getWeeklySchedules();
    }

    @Override
    public StudyGroupResponse response() {
        return StudyGroupResponse.builder()
                .id(this.id)
                .title(this.title)
                .description(this.description)
                .maxMembers(this.maxMembers)
                .meetingMethod(this.meetingMethod)
                .recruitmentStatus(this.recruitmentStatus)
                .feeType(this.feeType)
                .amount(this.amount)
                .leader(UserResponse.builder()
                        .id(this.leader.getId())
                        .build())
                .address(SggResponse.builder()
                        .id(this.address.getId())
                        .build())
                .subjectArea(SubjectAreaResponse.builder()
                        .id(this.subjectArea.getId())
                        .build())
                .keywords(this.keywords)
                .weeklySchedules(this.weeklySchedules)
                .isDeleted(this.isDeleted)
                .createdAt(this.createdAt)
                .createdBy(this.createdBy)
                .updatedAt(this.updatedAt)
                .updatedBy(this.updatedBy)
                .build();
    }
}
