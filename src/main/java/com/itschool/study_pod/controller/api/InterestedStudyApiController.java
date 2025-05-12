package com.itschool.study_pod.controller.api;

import com.itschool.study_pod.controller.base.CrudController;
import com.itschool.study_pod.dto.Header;
import com.itschool.study_pod.dto.request.interestedstudy.InterestedStudyRequest;
import com.itschool.study_pod.dto.response.InterestedStudyResponse;
import com.itschool.study_pod.entity.InterestedStudy;
import com.itschool.study_pod.service.InterestedStudyService;
import com.itschool.study_pod.service.base.CrudService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
}
