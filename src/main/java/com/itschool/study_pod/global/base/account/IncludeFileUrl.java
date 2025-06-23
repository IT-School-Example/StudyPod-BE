package com.itschool.study_pod.global.base.account;

import com.itschool.study_pod.global.base.BaseEntity;
import com.itschool.study_pod.global.base.crud.Convertible;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@MappedSuperclass
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
@Getter
public abstract class IncludeFileUrl<Req, Res> extends BaseEntity implements Convertible<Req, Res> {

    @Column
    protected String fileUrl;

    // 추가
    @Column(name = "is_deleted", insertable = false, updatable = false)
    protected boolean deleted = false;

    // 기존 파일 경로 수정
    public void updateFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    // 추가
    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

}
