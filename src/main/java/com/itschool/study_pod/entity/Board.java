package com.itschool.study_pod.entity;

import com.itschool.study_pod.dto.request.board.BoardRequest;
import com.itschool.study_pod.dto.response.AdminResponse;
import com.itschool.study_pod.dto.response.BoardResponse;
import com.itschool.study_pod.dto.response.StudyGroupResponse;
import com.itschool.study_pod.dto.response.UserResponse;
import com.itschool.study_pod.entity.base.BaseEntity;
import com.itschool.study_pod.enumclass.BoardCategory;
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
@Table(name = "boards")
@SQLDelete(sql = "UPDATE boards SET is_deleted = true WHERE board_id = ?")
@Where(clause = "is_deleted = false")
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
                    .title(request.getTitle())
                    .content(request.getContent())
                    .category(request.getCategory())
                    .user(User.withId(request.getUser().getId()))
                    .admin(Admin.withId(request.getAdmin().getId()))
                    .studyGroup(StudyGroup.withId(request.getStudyGroup().getId()))
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
                .user(this.user != null?
                        UserResponse.withId(this.user.getId())
                        : null)
                .admin(this.admin != null?
                        AdminResponse.withId(this.admin.getId())
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
    public void update(BoardRequest request) {
        this.title = request.getTitle();
        this.content = request.getContent();
    }

    public static Board withId(Long id) {
        return Board.builder()
                .id(id)
                .build();
    }

}
