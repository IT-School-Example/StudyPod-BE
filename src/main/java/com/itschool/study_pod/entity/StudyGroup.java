package com.itschool.study_pod.entity;

import com.itschool.study_pod.embedable.WeeklySchedule;
import com.itschool.study_pod.entity.address.Sgg;
import com.itschool.study_pod.entity.base.BaseEntity;
import com.itschool.study_pod.enumclass.FeeType;
import com.itschool.study_pod.enumclass.MeetingMethod;
import com.itschool.study_pod.enumclass.RecruitmentStatus;
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
public class StudyGroup extends BaseEntity {
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

    @ElementCollection
    @CollectionTable(name = "study_group_keywords",
            joinColumns = @JoinColumn(name = "study_group_id"))
    @Column(name = "keyword_name", nullable = false)
    private Set<String> keywords = new HashSet<>();

    @ElementCollection
    @CollectionTable(name = "study_group_weekly_schedules",
            joinColumns = @JoinColumn(name = "study_group_id"))
    private Set<WeeklySchedule> weeklySchedules = new HashSet<>();
}
