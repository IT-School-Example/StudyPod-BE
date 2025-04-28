package com.itschool.study_pod.entity.base;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
@Getter
public abstract class BaseEntity {
    @CreatedBy
    @Column(updatable = false, nullable = false)
    protected String createdBy;

    @CreatedDate
    @Column(updatable = false, nullable = false) // , columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP"
    protected LocalDateTime createdAt;

    @LastModifiedBy
    @Column(nullable = false)
    protected String updatedBy;

    @LastModifiedDate
    @Column(nullable = false) // , columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP"
    protected LocalDateTime updatedAt;

    @Column(nullable = false) // , columnDefinition = "BOOLEAN DEFAULT FALSE"
    protected boolean isDeleted;

    public void softDelete() {
        this.isDeleted = true;
    }
}
