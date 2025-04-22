package com.itschool.study_pod.entity.address;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    @Column(columnDefinition = "CHAR(2)", insertable = false, updatable = false)
    private String sidoCd;

    @Column(columnDefinition = "character varying(100)", insertable = false, updatable = false)
    private String sidoNm;
}
