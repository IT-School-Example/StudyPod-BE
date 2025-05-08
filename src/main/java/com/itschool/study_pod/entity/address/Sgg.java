package com.itschool.study_pod.entity.address;

import com.itschool.study_pod.dto.request.AdminRequest;
import com.itschool.study_pod.dto.request.address.SggRequest;
import com.itschool.study_pod.dto.response.address.SggResponse;
import com.itschool.study_pod.entity.Admin;
import com.itschool.study_pod.ifs.Convertible;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(schema = "address")
public class Sgg implements Convertible<SggRequest, SggResponse> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sgg_id", insertable = false, updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sido_cd", insertable = false, updatable = false)
    private Sido sido;  // `Sido` 엔티티와의 외래 키 관계

    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(length = 3, insertable = false, updatable = false)
    private String sggCd;

    @Column(columnDefinition = "character varying(100)")
    private String sggNm;

    @Deprecated
    public static Sgg of(SggRequest request) { // create용
        if(request != null) {
            return Sgg.builder()
                    .id(request.getId())
                    .sido(Sido.of(request.getSido()))
                    .sggCd(request.getSggCd())
                    .sggNm(request.getSggNm())
                    .build();
        }
        return null;
    }

    public static Sgg withId(Long id) {
        return Sgg.builder()
                .id(id)
                .build();

    }

    @Deprecated
    @Override
    public void update(SggRequest request) {
        throw new RuntimeException("사용하지 않음");
    }

    // 조회만 허용
    @Override
    public SggResponse response() {
        return SggResponse.builder()
                .id(this.id)
                .sido(this.sido.response())
                .sggCd(this.sggCd)
                .sggNm(this.sggNm)
                .build();
    }
}
