package com.itschool.study_pod.entity;

import com.itschool.study_pod.dto.request.FaqRequest;
import com.itschool.study_pod.dto.response.AdminResponse;
import com.itschool.study_pod.dto.response.FaqResponse;
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
@Table(name = "faqs")
@SQLDelete(sql = "UPDATE faqs SET is_deleted = true WHERE faq_id = ?")
@Where(clause = "is_deleted = false")
public class Faq extends BaseEntity implements Convertible<FaqRequest, FaqResponse> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "faq_id")
    private Long id;

    @Column(nullable = false)
    private String question;

    @Column(nullable = false)
    private String answer;

    @Column(nullable = false)
    private Boolean visible; // 사용자에게 노출 여부

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id", nullable = false)
    private Admin admin;

    public static Faq of (FaqRequest request) {
        if(request != null) {
            return Faq.builder()
                    .id(request.getId())
                    .question(request.getQuestion())
                    .answer(request.getAnswer())
                    .visible(request.getVisible())
                    .admin(Admin.of(request.getAdmin()))
                    .build();
        }
        return null;
    }

    @Override
    public FaqResponse response() {
        return FaqResponse.builder()
                .id(this.id)
                .question(this.question)
                .answer(this.answer)
                .visible(this.visible)
                .admin(AdminResponse.builder()
                        .id(this.admin.getId())
                        .build())
                .createdAt(this.createdAt)
                .createdBy(this.createdBy)
                .updatedAt(this.updatedAt)
                .updatedBy(this.updatedBy)
                .build();
    }

    @Override
    public void update(FaqRequest request) {
        this.question = request.getQuestion();
        this.answer = request.getAnswer();
        this.visible = request.getVisible();
    }
}
