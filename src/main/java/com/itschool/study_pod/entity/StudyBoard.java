package com.itschool.study_pod.entity;

import com.itschool.study_pod.dto.request.studyboard.StudyBoardRequest;
import com.itschool.study_pod.dto.response.*;
import com.itschool.study_pod.entity.base.BaseEntity;
import com.itschool.study_pod.enumclass.StudyBoardCategory;
import com.itschool.study_pod.ifs.Convertible;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "study_boards")
//@SQLDelete(sql = "UPDATE study-boards SET is_deleted = true WHERE study_board_id = ?")
@Where(clause = "is_deleted = false")
public class StudyBoard extends BaseEntity implements Convertible<StudyBoardRequest, StudyBoardResponse>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "study_board_id")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StudyBoardCategory studyBoardCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "study_group_id")
    private StudyGroup studyGroup;

    public static StudyBoard of (StudyBoardRequest request) {
        return StudyBoard.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .studyBoardCategory(request.getStudyBoardCategory())
                .user(request.getUser() != null?
                        User.withId(request.getUser().getId())
                        : null)
                .studyGroup(request.getStudyGroup() != null?
                        StudyGroup.withId(request.getStudyGroup().getId())
                        : null)
                .build();
    }

    @Override
    public StudyBoardResponse response() {
        return StudyBoardResponse.builder()
                .id(this.id)
                .title(this.title)
                .content(this.content)
                .studyBoardCategory(this.studyBoardCategory)
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
    public void update(StudyBoardRequest request) {
        this.title = request.getTitle();
        this.content = request.getContent();

        this.user = request.getUser() != null ? User.withId(request.getUser().getId()) : null;
        this.studyGroup = request.getStudyGroup() != null ? StudyGroup.withId(request.getStudyGroup().getId()) : null;
    }

    public static StudyBoard withId(Long id) {
        return StudyBoard.builder()
                .id(id)
                .build();
    }
}
