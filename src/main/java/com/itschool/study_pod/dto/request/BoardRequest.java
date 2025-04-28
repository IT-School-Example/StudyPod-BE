package com.itschool.study_pod.dto.request;

import com.itschool.study_pod.enumclass.BoardCategory;
import lombok.*;

@Data // 종합선물세트 : @Getter, @Setter, @ToString, @EqualsAndHashCode, @RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class BoardRequest {

    // fk용으로 필요
    private Long id;

    private String title;

    private String content;

    private BoardCategory category;

    private UserRequest user;

    private AdminRequest admin;

    private StudyGroupRequest studyGroup;

}
