package com.itschool.study_pod.service.address;

import com.itschool.study_pod.dto.ApiResponse;
import com.itschool.study_pod.dto.request.address.SidoRequest;
import com.itschool.study_pod.dto.response.address.SidoResponse;
import com.itschool.study_pod.entity.address.Sido;
import com.itschool.study_pod.repository.address.SidoRepository;
import com.itschool.study_pod.service.base.CrudService;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class SidoService extends CrudService<SidoRequest, SidoResponse, Sido> {

    public SidoService(SidoRepository baseRepository) {
        super(baseRepository);
    }

    @Override
    protected Sido toEntity(SidoRequest requestEntity) {
        return Sido.of(requestEntity);
    }

    @Override
    public ApiResponse<SidoResponse> create(RequestEntity<SidoRequest> request) {
        throw new RuntimeException("Sido 생성 허용 불가");
    }

    @Override
    public ApiResponse<SidoResponse> update(Long id, RequestEntity<SidoRequest> request) {
        throw new RuntimeException("Sido 수정 허용 불가");
    }

    @Override
    public ResponseEntity delete(Long id) {
        throw new RuntimeException("Sido 삭제 허용 불가");
    }
}
