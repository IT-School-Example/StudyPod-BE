package com.itschool.study_pod.entity;

import com.itschool.study_pod.entity.base.BaseEntity;
import com.itschool.study_pod.enumclass.FeeType;
import com.itschool.study_pod.enumclass.MeetingMethod;
import com.itschool.study_pod.enumclass.RecruitmentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "study_groups")
public class StudyGroups extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "study_group_id")
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description",length = 255)
    private String description;

    @Column(name = "max_members",nullable = false)
    private Integer maxMembers;

    @Enumerated(EnumType.STRING)
    @Column(name = "meeting_method",nullable = false)
    private MeetingMethod meetingMethod;

    @Enumerated(EnumType.STRING)
    @Column(name = "recruitment_status",nullable = false)
    private RecruitmentStatus recruitmentStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "fee_type")
    private FeeType feeType;

    @Column(name = "amount")
    private Long amount;

    /*@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "leader_id", referencedColumnName = "user_id")
    private User leader;*/
    @Column(name = "leader_id")
    private Long leader_id;

    /*@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id", referencedColumnName = "address_id")*/
    @Column(name = "address_id")
    private Long address_id;

    /*@ManyToOne(fetch = FetchType.LAZY, optional = false) // subject_area_id는 NOT NULL
    @JoinColumn(name = "subject_area_id", referencedColumnName = "subject_area_id", nullable = false)*/
    @Column(name = "subject_area_id")
    private Long subject_area_id;
}
