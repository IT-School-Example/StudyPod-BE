package com.itschool.study_pod.entity;

import com.itschool.study_pod.dto.request.BoardRequest;
import com.itschool.study_pod.dto.response.BoardResponse;
import com.itschool.study_pod.entity.base.BaseEntity;
import com.itschool.study_pod.enumclass.BoardCategory;
import com.itschool.study_pod.ifs.Convertible;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "boards")
public class Board extends BaseEntity implements Convertible<BoardRequest, BoardResponse> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BoardCategory category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id")
    private Admin admin;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "study_group_id")
    private StudyGroup studyGroup;

    // 요청 DTO -> Entity 로 변환하는 메서드
    public static Board of (BoardRequest request) {
        if(request != null) {
            return Board.builder()
                    .id(request.getId())
                    .title(request.getTitle())
                    .content(request.getContent())
                    .category(request.getCategory())
                    .user(User.of(request.getUser()))
                    .admin(Admin.of(request.getAdmin()))
                    .studyGroup(StudyGroup.of(request.getStudyGroup()))
                    .build();
        }
        return null;
    }

    @Override
    public BoardResponse response() {
        return BoardResponse.builder()
                .id(this.id)
                .title(this.title)
                .content(this.content)
                .category(BoardCategory.FREE)
                .user(this.user.response())
                .admin(this.admin.response())
                .studyGroup(this.studyGroup.response())
                .isDeleted(this.isDeleted)
                .createdAt(this.createdAt)
                .createdBy(this.createdBy)
                .updatedAt(this.updatedAt)
                .updatedBy(this.updatedBy)
                .build();
    }

    @Override
    public void update(BoardRequest request) {
        this.title = request.getTitle();
        this.content = request.getContent();
    }
}
