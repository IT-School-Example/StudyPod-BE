package com.itschool.study_pod.domain.interestedsubject.controller;

import com.itschool.study_pod.global.base.crud.CrudController;
import com.itschool.study_pod.global.base.dto.Header;
import com.itschool.study_pod.domain.interestedsubject.dto.request.InterestedSubjectRequest;
import com.itschool.study_pod.domain.interestedsubject.dto.response.InterestedSubjectResponse;
import com.itschool.study_pod.domain.interestedsubject.service.InterestedSubjectService;
import com.itschool.study_pod.domain.interestedsubject.entity.InterestedSubject;
import com.itschool.study_pod.global.base.crud.CrudService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "관심 주제", description = "관심 주제 API")
@RequestMapping("/api/interested-Subjects")
public class InterestedSubjectApiController extends CrudController<InterestedSubjectRequest, InterestedSubjectResponse, InterestedSubject> {

    private final InterestedSubjectService interestedSubjectService;

    @Override
    protected CrudService<InterestedSubjectRequest, InterestedSubjectResponse, InterestedSubject> getBaseService() {
        return interestedSubjectService;
    }

    @Override
    @Deprecated
    public Header<InterestedSubjectResponse> update(Long id, Header<InterestedSubjectRequest> request) {
        throw new IllegalArgumentException("해당 연결 테이블에서 업데이트는 허용하지 않습니다. 삭제 후 다시 생성하세요");
    }
}
