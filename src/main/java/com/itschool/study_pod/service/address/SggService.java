package com.itschool.study_pod.service.address;

import com.itschool.study_pod.dto.ApiResponse;
import com.itschool.study_pod.dto.request.address.SggRequest;
import com.itschool.study_pod.dto.response.address.SggResponse;
import com.itschool.study_pod.entity.address.Sgg;
import com.itschool.study_pod.repository.address.SggRepository;
import com.itschool.study_pod.repository.address.SidoRepository;
import com.itschool.study_pod.service.base.CrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SggService extends CrudService<SggRequest, SggResponse, Sgg> {

    private final SggRepository sggRepository;

    private final SidoRepository sidoRepository;

    @Override
    protected JpaRepository<Sgg, Long> getBaseRepository() {
        return sggRepository;
    }

    @Override
    protected Sgg toEntity(SggRequest requestEntity) {
        return Sgg.of(requestEntity);
    }

    @Override
    @Deprecated
    public ApiResponse<SggResponse> create(RequestEntity<SggRequest> request) {
        throw new RuntimeException("Sgg 생성 허용 불가");
    }

    @Override
    @Deprecated
    public ApiResponse<SggResponse> update(Long id, RequestEntity<SggRequest> request) {
        throw new RuntimeException("Sgg 수정 허용 불가");
    }

    @Override
    @Deprecated
    public ApiResponse<Void> delete(Long id) {
        throw new RuntimeException("Sgg 삭제 허용 불가");
    }
}
