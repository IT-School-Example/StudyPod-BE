package com.itschool.study_pod.entity;

import com.itschool.study_pod.dto.request.CommentRequest;
import com.itschool.study_pod.dto.response.BoardResponse;
import com.itschool.study_pod.dto.response.CommentResponse;
import com.itschool.study_pod.dto.response.UserResponse;
import com.itschool.study_pod.entity.base.BaseEntity;
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
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public static Comment of(CommentRequest request) { // create용
        if(request != null) {
            return Comment.builder()
                    .content(request.getContent())
                    .board(Board.withId(request.getBoard().getId()))
                    .user(User.withId(request.getUser().getId()))
                    .parentComment(Comment.withId(request.getParentComment().getId()))
                    .build();
        }
        return null;
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
        this.content = request.getContent();
    }


    public static Comment withId(Long id) {
        return Comment.builder()
                .id(id)
                .build();
    }

}
