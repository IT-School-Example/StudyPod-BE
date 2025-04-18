package com.itschool.study_pod.enumclass;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum MeetingMethod {
    ONLINE("온라인"), 
    OFFLINE("오프라인"), 
    BOTH("혼합");

    private final String description;
}
