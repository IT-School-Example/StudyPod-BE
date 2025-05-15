package com.itschool.study_pod.controller.api.address;

import com.itschool.study_pod.controller.base.CrudController;
import com.itschool.study_pod.dto.Header;
import com.itschool.study_pod.dto.request.address.SggRequest;
import com.itschool.study_pod.dto.response.address.SggResponse;
import com.itschool.study_pod.entity.address.Sgg;
import com.itschool.study_pod.service.address.SggService;
import com.itschool.study_pod.service.base.CrudService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("denyAll()")
    public Header<SggResponse> create(Header<SggRequest> request) {
        throw new RuntimeException("Sgg 생성 접근 불가");
    }

    @Override
    @Deprecated
    @PreAuthorize("denyAll()")
    public Header<SggResponse> update(Long id, Header<SggRequest> request) {
        throw new RuntimeException("Sgg 수정 접근 불가");
    }

    @Override
    @Deprecated
    @PreAuthorize("denyAll()")
    public Header<Void> delete(Long id) {
        throw new RuntimeException("Sgg 삭제 접근 불가");
    }
}
