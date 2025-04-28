package com.itschool.study_pod.entity;

import com.itschool.study_pod.dto.request.Board.BoardCreateRequest;
import com.itschool.study_pod.dto.request.Board.BoardRequest;
import com.itschool.study_pod.dto.request.Board.BoardUpdateRequest;
import com.itschool.study_pod.dto.response.BoardResponse;
import com.itschool.study_pod.dto.response.UserResponse;
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
    public static Board of (BoardCreateRequest request, User user, Admin admin, StudyGroup studyGroup) {
        return Board.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .category(request.getCategory())
                .user(user)
                .admin(admin)
                .studyGroup(studyGroup)
                .build();
    }

    public static Board of(BoardRequest request) { // create용
        return Board.builder()
                .build();
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
                .build();
    }

    @Override
    public void update(BoardRequest request) {
        if(request instanceof BoardUpdateRequest updateRequest) {
            this.title = updateRequest.getTitle();
            this.content = updateRequest.getContent();
        } else {
            throw new IllegalArgumentException("지원하지 않는 요청 타입입니다: " + request.getClass().getSimpleName());
        }
    }
}
