package com.itschool.study_pod.entity;

import com.itschool.study_pod.dto.request.StudyGroup.StudyGroupRequest;
import com.itschool.study_pod.dto.request.User.UserCreateRequest;
import com.itschool.study_pod.dto.response.StudyGroupResponse;
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
    private Sgg addressId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_area_id", nullable = false)
    private SubjectArea subjectArea;

    @Builder.Default
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
        return StudyGroup.builder()
                .build();
    }

    @Override
    public void update(StudyGroupRequest request) {

    }

    @Override
    public StudyGroupResponse response() {
        return null;
    }
}
