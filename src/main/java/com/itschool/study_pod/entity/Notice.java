package com.itschool.study_pod.entity;

import com.itschool.study_pod.dto.request.board.NoticeRequest;
import com.itschool.study_pod.dto.response.NoticeResponse;
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
@Table(name = "notices")
@SQLDelete(sql = "UPDATE notices SET is_deleted = true WHERE notice_id = ?")
@Where(clause = "is_deleted = false")
public class Notice extends Post implements Convertible<NoticeRequest, NoticeResponse> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notice_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id")
    protected Admin admin;

    // 요청 DTO -> Entity 로 변환하는 메서드
    public static Notice of (NoticeRequest request) {
        return Notice.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .admin(request.getAdmin() != null?
                        Admin.withId(request.getAdmin().getId())
                        : null)
                .build();
    }

    @Override
    public void update(NoticeRequest request) {

    }

    @Override
    public NoticeResponse response() {
        return null;
    }
}
