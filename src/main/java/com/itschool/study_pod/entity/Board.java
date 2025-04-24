package com.itschool.study_pod.entity;

import com.itschool.study_pod.dto.request.Board.BoardCreateRequest;
import com.itschool.study_pod.dto.request.Board.BoardRequest;
import com.itschool.study_pod.dto.request.Board.BoardUpdateRequest;
import com.itschool.study_pod.entity.base.BaseEntity;
import com.itschool.study_pod.enumclass.BoardCategory;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "boards")
public class Board extends BaseEntity {
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

    public static Board of (BoardCreateRequest request) { // create용
        return Board.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .category(request.getCategory())
                .user(User.builder()
                        .id(request.getUserId())
                        .build())
                .admin(Admin.builder()
                        .id(request.getAdminId())
                        .build())
                .studyGroup(StudyGroup.builder()
                        .id(request.getStudyGroupId())
                        .build())
                .build();
    }

    // update용
    public void update(BoardRequest request) {
        if(request instanceof BoardUpdateRequest informationRequest) {
            this.title = informationRequest.getTitle();
            this.content = informationRequest.getContent();
        } else {
            throw new IllegalArgumentException("지원하지 않는 요청 타입입니다: " + request.getClass().getSimpleName());
        }
    }
}
