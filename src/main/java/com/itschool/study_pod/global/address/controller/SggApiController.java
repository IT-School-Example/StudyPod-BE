package com.itschool.study_pod.global.address.controller;

import com.itschool.study_pod.global.base.crud.CrudController;
import com.itschool.study_pod.global.base.dto.Header;
import com.itschool.study_pod.global.address.dto.request.SggRequest;
import com.itschool.study_pod.global.address.dto.response.SggResponse;
import com.itschool.study_pod.global.address.entity.Sgg;
import com.itschool.study_pod.global.address.service.SggService;
import com.itschool.study_pod.global.base.crud.CrudService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "시군구", description = "시군구 주소 API")
@RequestMapping("/api/sgg")
public class SggApiController extends CrudController<SggRequest, SggResponse, Sgg> {

    private final SggService sggService;

    @Override
    protected CrudService<SggRequest, SggResponse, Sgg> getBaseService() {
        return sggService;
    }

    @Override
    @Deprecated
    public Header<SggResponse> create(Header<SggRequest> request) {
        throw new RuntimeException("Sgg 생성 접근 불가");
    }

    @Override
    @Deprecated
    public Header<SggResponse> update(Long id, Header<SggRequest> request) {
        throw new RuntimeException("Sgg 수정 접근 불가");
    }

    @Override
    @Deprecated
    public Header<Void> delete(Long id) {
        throw new RuntimeException("Sgg 삭제 접근 불가");
    }
}
