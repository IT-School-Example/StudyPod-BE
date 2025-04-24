package com.itschool.study_pod.entity.address;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Getter
@NoArgsConstructor
@Table(schema = "address",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"sidoNm"})
        }
)
public class Sido {
    @Id
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(length = 2, insertable = false, updatable = false)
    private String sidoCd;

    @Column(columnDefinition = "character varying(100)", insertable = false, updatable = false)
    private String sidoNm;
}
