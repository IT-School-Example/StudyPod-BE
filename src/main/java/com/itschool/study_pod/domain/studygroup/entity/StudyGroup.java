package com.itschool.study_pod.domain.studygroup.entity;

import com.itschool.study_pod.domain.admin.entity.Admin;
import com.itschool.study_pod.domain.studygroup.dto.request.StudyGroupRequest;
import com.itschool.study_pod.domain.studygroup.dto.response.StudyGroupResponse;
import com.itschool.study_pod.domain.subjectarea.entity.SubjectArea;
import com.itschool.study_pod.domain.user.entity.User;
import com.itschool.study_pod.domain.subjectarea.dto.response.SubjectAreaResponse;
import com.itschool.study_pod.domain.user.dto.response.UserResponse;
import com.itschool.study_pod.global.address.dto.response.SidoResponse;
import com.itschool.study_pod.global.address.entity.Sido;
import com.itschool.study_pod.global.base.account.IncludeFileUrl;
import com.itschool.study_pod.global.embedable.WeeklySchedule;
import com.itschool.study_pod.global.enumclass.FeeType;
import com.itschool.study_pod.global.enumclass.MeetingMethod;
import com.itschool.study_pod.global.enumclass.RecruitmentStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
@Table(name = "study_groups")
@SQLDelete(sql = "UPDATE study_groups SET is_deleted = true WHERE study_group_id = ?")
@Where(clause = "is_deleted = false")
public class StudyGroup extends IncludeFileUrl<StudyGroupRequest, StudyGroupResponse> {
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

    // ✅ 추가됨: 관리자 정지 여부
    @Column(name = "is_suspended", nullable = false)
    private Boolean suspended;

    // ✅ 추가됨: 정지 사유
    @Column(name = "suspend_reason")
    private String suspendReason;

    // 추가
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id")
    private Admin admin;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MeetingMethod meetingMethod;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RecruitmentStatus recruitmentStatus;

    // 이미지 업로드 기능 위해 추가
    /*@Column(name = "image_url")
    private String imageUrl;*/


    @Enumerated(EnumType.STRING)
    private FeeType feeType;

    private Long amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "leader_id", referencedColumnName = "account_id", nullable = false)
    private User leader;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sido_cd", referencedColumnName = "sido_cd")
    private Sido sido;

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
        return StudyGroup.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .maxMembers(request.getMaxMembers())
                .meetingMethod(request.getMeetingMethod())
                .recruitmentStatus(request.getRecruitmentStatus())
                .feeType(request.getFeeType())
                .amount(request.getAmount())
                .leader(User.withId(request.getLeader().getId()))
                .sido(Sido.withId(request.getSido().getSidoCd()))
                .subjectArea(SubjectArea.withId(request.getSubjectArea().getId()))
                .keywords(new HashSet<>(request.getKeywords()))
                .weeklySchedules(new HashSet<>(request.getWeeklySchedules()))
                // .fileUrl(request.getFileUrl())
                .suspended(false)
                .build();
    }

    public static StudyGroup withId(Long id) {
        return StudyGroup.builder()
                .id(id)
                .build();
    }

    @Override
    public void update(StudyGroupRequest request) {
        this.title = request.getTitle();
        this.description = request.getDescription();
        this.maxMembers = request.getMaxMembers();
        this.meetingMethod = request.getMeetingMethod();
        this.recruitmentStatus = request.getRecruitmentStatus();
        this.sido = Sido.withId(request.getSido().getSidoCd());
        this.subjectArea = SubjectArea.withId(request.getSubjectArea().getId());
        this.feeType = request.getFeeType();
        this.amount = request.getAmount();
        this.keywords = new HashSet<>(request.getKeywords());
        this.weeklySchedules = new HashSet<>(request.getWeeklySchedules());

        // fk 변경 위험, 스터디장 변경은 fetch로 스터디장만 변경하는 다른 api 로직 생성 필요
        // this.leader = User.of(request.getLeader());
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
                .leader(UserResponse.withId(this.leader.getId()))
                .sido(SidoResponse.withId(this.sido.getSidoCd()))
                .subjectArea(SubjectAreaResponse.withId(this.subjectArea.getId()))
                .keywords(this.keywords)
                .weeklySchedules(this.weeklySchedules)
                .fileUrl(this.fileUrl)
                .createdAt(this.createdAt)
                .createdBy(this.createdBy)
                .updatedAt(this.updatedAt)
                .updatedBy(this.updatedBy)
                .build();
    }


    public void updateFromRequest(StudyGroupRequest req, User leader, String sidoCd, SubjectArea subjectArea) {
        this.title = req.getTitle();
        this.description = req.getDescription();
        this.maxMembers = req.getMaxMembers();
        this.meetingMethod = req.getMeetingMethod();
        this.recruitmentStatus = req.getRecruitmentStatus();
        this.feeType = req.getFeeType();
        this.amount = req.getAmount();
        this.leader = leader;
        this.sido = Sido.withId(sidoCd);
        this.subjectArea = subjectArea;
        this.keywords = req.getKeywords();
        this.weeklySchedules = req.getWeeklySchedules();
    }


    public void setSuspended(boolean suspended) {
        this.suspended = suspended;
    }

    public void setSuspendReason(String suspendReason) {
        this.suspendReason = suspendReason;
    }

}