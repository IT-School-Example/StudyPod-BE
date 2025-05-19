package com.itschool.study_pod.domain.adminboard.entity;

import com.itschool.study_pod.domain.admin.entity.Admin;
import com.itschool.study_pod.domain.adminboard.dto.request.AdminBoardRequest;
import com.itschool.study_pod.domain.adminboard.dto.response.AdminBoardResponse;
import com.itschool.study_pod.domain.admin.dto.response.AdminResponse;
import com.itschool.study_pod.global.base.BaseEntity;
import com.itschool.study_pod.global.enumclass.AdminBoardCategory;
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
@Table(name = "admin_boards")
@SQLDelete(sql = "UPDATE admin_boards SET is_deleted = true WHERE admin_board_id = ?")
@Where(clause = "is_deleted = false")
public class AdminBoard extends BaseEntity implements Convertible<AdminBoardRequest, AdminBoardResponse>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "admin_board_id")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AdminBoardCategory adminBoardCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id", nullable = false)
    private Admin admin;

    public static AdminBoard of (AdminBoardRequest request) {
        return AdminBoard.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .adminBoardCategory(request.getAdminBoardCategory())
                .admin(Admin.withId(request.getAdmin().getId()))
                .build();
    }

    @Override
    public AdminBoardResponse response() {
        return AdminBoardResponse.builder()
                .id(this.id)
                .title(this.title)
                .content(this.content)
                .adminBoardCategory(this.adminBoardCategory)
                .admin(AdminResponse.withId(this.admin.getId()))
                .createdAt(this.createdAt)
                .createdBy(this.createdBy)
                .updatedAt(this.updatedAt)
                .updatedBy(this.updatedBy)
                .build();
    }

    @Override
    public void update(AdminBoardRequest request) {
        this.title = request.getTitle();
        this.content = request.getContent();

        this.admin = Admin.withId(request.getAdmin().getId());
    }

    public static AdminBoard withId(Long id) {
        return AdminBoard.builder()
                .id(id)
                .build();
    }
}
