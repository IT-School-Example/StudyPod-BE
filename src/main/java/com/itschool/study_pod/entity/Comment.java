package com.itschool.study_pod.entity;

import com.itschool.study_pod.dto.request.Comment.CommentRequest;
import com.itschool.study_pod.dto.request.Enrollment.EnrollmentRequest;
import com.itschool.study_pod.dto.response.CommentResponse;
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

    public static Comment of(CommentRequest request, Board board, User user, Comment parentComment) { // create용
        return Comment.builder()
                .content(request.getContent())
                .board(board)
                .user(user)
                .parentComment(parentComment)
                .build();
    }

    @Override
    public CommentResponse response() {
        return CommentResponse.builder()
                .id(this.id)
                .content(this.content)
                .board(this.board.response())
                .user(this.user.response())
                .parentComment(this.parentComment.response())
                .build();
    }

    @Override
    public void update(CommentRequest request) {

    }
}
