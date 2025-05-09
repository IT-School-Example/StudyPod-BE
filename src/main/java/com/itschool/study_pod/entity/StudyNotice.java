package com.itschool.study_pod.entity;

import com.itschool.study_pod.dto.request.board.StudyNoticeRequest;
import com.itschool.study_pod.dto.response.StudyNoticeResponse;
import com.itschool.study_pod.dto.response.StudyGroupResponse;
import com.itschool.study_pod.dto.response.UserResponse;
import com.itschool.study_pod.entity.base.Post;
import com.itschool.study_pod.enumclass.BoardCategory;
import com.itschool.study_pod.ifs.Convertible;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@SuperBuilder
@Table(name = "boards")
@SQLDelete(sql = "UPDATE boards SET is_deleted = true WHERE board_id = ?")
@Where(clause = "is_deleted = false")
public class StudyNotice extends Post implements Convertible<StudyNoticeRequest, StudyNoticeResponse> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "study_group_id")
    private StudyGroup studyGroup;

    // 요청 DTO -> Entity 로 변환하는 메서드
    public static StudyNotice of (StudyNoticeRequest request) {
        return StudyNotice.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .user(request.getUser() != null?
                        User.withId(request.getUser().getId())
                        : null)
                .studyGroup(request.getStudyGroup() != null?
                        StudyGroup.withId(request.getStudyGroup().getId())
                        : null)
                .build();
    }

    @Override
    public StudyNoticeResponse response() {
        return StudyNoticeResponse.builder()
                .id(this.id)
                .title(this.title)
                .content(this.content)
                .category(BoardCategory.FREE)
                .user(this.user != null?
                        UserResponse.withId(this.user.getId())
                        : null)
                .studyGroup(this.studyGroup != null?
                        StudyGroupResponse.withId(this.studyGroup.getId())
                        : null)
                .createdAt(this.createdAt)
                .createdBy(this.createdBy)
                .updatedAt(this.updatedAt)
                .updatedBy(this.updatedBy)
                .build();
    }


    @Override
    public void update(StudyNoticeRequest request) {
        this.title = request.getTitle() != null? request.getTitle() : this.title;
        this.content = request.getContent() != null? request.getContent() : this.content;

        // 카테고리 변경은 위험
        // this.category = request.getCategory() != null? request.getCategory() : this.category;

        this.user = request.getUser() != null ? User.withId(request.getUser().getId()) : null;
        this.studyGroup = request.getStudyGroup() != null ? StudyGroup.withId(request.getStudyGroup().getId()) : null;
    }

    public static StudyNotice withId(Long id) {
        return StudyNotice.builder()
                .id(id)
                .build();
    }
}
