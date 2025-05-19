package com.itschool.study_pod.domain.comment.entity;

import com.itschool.study_pod.domain.board.entity.Board;
import com.itschool.study_pod.domain.comment.dto.request.CommentRequest;
import com.itschool.study_pod.domain.board.dto.response.BoardResponse;
import com.itschool.study_pod.domain.comment.dto.response.CommentResponse;
import com.itschool.study_pod.domain.user.dto.response.UserResponse;
import com.itschool.study_pod.domain.user.entity.User;
import com.itschool.study_pod.global.base.BaseEntity;
import com.itschool.study_pod.global.base.crud.Convertible;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Table(name = "comments")
@SQLDelete(sql = "UPDATE comments SET is_deleted = true WHERE comment_id = ?")
@Where(clause = "is_deleted = false")
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
    @JoinColumn(name = "account_id", nullable = false)
    private User user;

    public static Comment of(CommentRequest request) { // create용
        return Comment.builder()
                .content(request.getContent())
                .board(Board.withId(request.getBoard().getId()))
                .user(User.withId(request.getUser().getId()))
                .parentComment(request.getParentComment() != null?
                        Comment.withId(request.getParentComment().getId())
                        : null)
                .build();
    }

    @Override
    public CommentResponse response() {
        return CommentResponse.builder()
                .id(this.id)
                .content(this.content)
                .board(BoardResponse.withId(this.board.getId()))
                .user(UserResponse.withId(this.user.getId()))
                .parentComment(
                        this.parentComment != null?
                            CommentResponse.withId(this.parentComment.getId())
                            : null)
                .createdAt(this.createdAt)
                .createdBy(this.createdBy)
                .updatedAt(this.updatedAt)
                .updatedBy(this.updatedBy)
                .build();
    }

    @Override
    public void update(CommentRequest request) {
        this.content = request.getContent() != null? request.getContent() : this.content;
        this.board = request.getBoard() != null ? Board.withId(request.getBoard().getId()) : this.board;
        this.user = request.getUser() != null ? User.withId(request.getUser().getId()) : this.user;
        this.parentComment = request.getParentComment() != null ? Comment.withId(request.getParentComment().getId()) : null;
    }


    public static Comment withId(Long id) {
        return Comment.builder()
                .id(id)
                .build();
    }

}
