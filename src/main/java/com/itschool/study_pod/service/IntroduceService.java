package com.itschool.study_pod.service;

import com.itschool.study_pod.dto.request.IntroduceRequest;
import com.itschool.study_pod.dto.response.IntroduceResponse;
import com.itschool.study_pod.entity.Introduce;
import com.itschool.study_pod.repository.IntroduceRepository;
import com.itschool.study_pod.service.base.CrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IntroduceService extends CrudService<IntroduceRequest, IntroduceResponse, Introduce> {
    private final IntroduceRepository introduceRepository;

    @Override
    protected JpaRepository<Introduce, Long> getBaseRepository() {
        return introduceRepository;
    }

    @Override
    protected Introduce toEntity(IntroduceRequest requestEntity) {
        return Introduce.of(requestEntity);
    }
}
