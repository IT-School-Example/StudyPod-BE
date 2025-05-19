package com.itschool.study_pod.global.address.service;

import com.itschool.study_pod.global.address.dto.request.SggRequest;
import com.itschool.study_pod.global.address.dto.response.SggResponse;
import com.itschool.study_pod.global.address.entity.Sgg;
import com.itschool.study_pod.global.address.repository.SggRepository;
import com.itschool.study_pod.global.address.repository.SidoRepository;
import com.itschool.study_pod.global.base.crud.CrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
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
    protected Sgg toEntity(SggRequest request) {
        return Sgg.of(request);
    }
}
