package com.itschool.study_pod.domain.interestedstudy.controller;

import com.itschool.study_pod.domain.interestedstudy.dto.request.InterestedStudyRequest;
import com.itschool.study_pod.domain.interestedstudy.dto.response.InterestedStudyResponse;
import com.itschool.study_pod.domain.interestedstudy.entity.InterestedStudy;
import com.itschool.study_pod.domain.interestedstudy.service.InterestedStudyService;
import com.itschool.study_pod.global.base.crud.CrudController;
import com.itschool.study_pod.global.base.crud.CrudService;
import com.itschool.study_pod.global.base.dto.Header;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "관심 스터디", description = "관심 스터디 API")
@RequestMapping("/api/interested-studies")
public class InterestedStudyApiController extends CrudController<InterestedStudyRequest, InterestedStudyResponse, InterestedStudy> {

    private final InterestedStudyService interestedStudyService;

    @Override
    protected CrudService<InterestedStudyRequest, InterestedStudyResponse, InterestedStudy> getBaseService() {
        return interestedStudyService;
    }

    @Override
    @Deprecated
    public Header<InterestedStudyResponse> update(Long id, Header<InterestedStudyRequest> request) {
        throw new IllegalArgumentException("해당 연결 테이블에서 업데이트는 허용하지 않습니다. 삭제 후 다시 생성하세요");
    }

    @Operation(summary = "관심목록", description = "관심있는 스터디 등록 및 해제")
    @PatchMapping("likes")
    public Header<InterestedStudyResponse> findByInterestedStudy(@RequestBody Header<InterestedStudyRequest> request) {
        return interestedStudyService.toggleInterestedStudy(request);

    }
}
