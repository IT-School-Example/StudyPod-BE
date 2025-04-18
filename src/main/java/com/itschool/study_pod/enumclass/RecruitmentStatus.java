package com.itschool.study_pod.enumclass;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum RecruitmentStatus {
    RECRUITING("모집 중"), 
    CLOSED("모집 마감");

    private final String description;
}
