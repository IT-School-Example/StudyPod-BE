package com.itschool.study_pod.entity;

import com.itschool.study_pod.dto.request.CommentRequest;
import com.itschool.study_pod.dto.response.BoardResponse;
import com.itschool.study_pod.dto.response.CommentResponse;
import com.itschool.study_pod.dto.response.UserResponse;
import com.itschool.study_pod.entity.base.BaseEntity;
import com.itschool.study_pod.ifs.Convertible;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "comments")
public class Comment extends BaseEntity implements Convertible<CommentRequest, CommentResponse> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Comment parentComment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public static Comment of(CommentRequest request) { // create용
        if(request != null) {
            return Comment.builder()
                    .id(request.getId())
                    .content(request.getContent())
                    .board(Board.of(request.getBoard()))
                    .user(User.of(request.getUser()))
                    .parentComment(Comment.of(request.getParentComment()))
                    .build();
        }
        return null;
    }

    @Override
    public CommentResponse response() {
        return CommentResponse.builder()
                .id(this.id)
                .content(this.content)
                .board(BoardResponse.builder()
                        .id(this.board.getId())
                        .build())
                .user(UserResponse.builder()
                        .id(this.user.getId())
                        .build())
                .parentComment(CommentResponse.builder()
                        .id(this.parentComment.getId())
                        .build())
                .isDeleted(this.isDeleted)
                .createdAt(this.createdAt)
                .createdBy(this.createdBy)
                .updatedAt(this.updatedAt)
                .updatedBy(this.updatedBy)
                .build();
    }

    @Override
    public void update(CommentRequest request) {
        this.content = request.getContent();
    }
}
