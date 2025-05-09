package com.itschool.study_pod.entity;

import com.itschool.study_pod.dto.request.board.FaqRequest;
import com.itschool.study_pod.dto.response.AdminResponse;
import com.itschool.study_pod.dto.response.FaqResponse;
import com.itschool.study_pod.entity.base.Post;
import com.itschool.study_pod.ifs.Convertible;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@SuperBuilder
@Table(name = "faqs")
@SQLDelete(sql = "UPDATE faqs SET is_deleted = true WHERE faq_id = ?")
@Where(clause = "is_deleted = false")
public class Faq extends Post implements Convertible<FaqRequest, FaqResponse> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "faq_id")
    private Long id;

    @Column(nullable = false)
    private Boolean visible; // 사용자에게 노출 여부

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id")
    protected Admin admin;

    public static Faq of (FaqRequest request) {
        return Faq.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .visible(request.getVisible())
                .admin(Admin.withId(request.getAdmin().getId()))
                .build();
    }

    @Override
    public FaqResponse response() {
        return FaqResponse.builder()
                .id(this.id)
                .title(this.title)
                .content(this.content)
                .visible(this.visible)
                .admin(AdminResponse.withId(this.admin.getId()))
                .createdAt(this.createdAt)
                .createdBy(this.createdBy)
                .updatedAt(this.updatedAt)
                .updatedBy(this.updatedBy)
                .build();
    }

    @Override
    public void update(FaqRequest request) {
        this.title = request.getTitle();
        this.content = request.getContent();
        this.visible = request.getVisible();
    }

    public Faq withId(Long id) {
        return Faq.builder()
                .id(id)
                .build();
    }
}
