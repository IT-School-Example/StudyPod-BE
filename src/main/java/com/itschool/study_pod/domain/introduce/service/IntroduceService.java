package com.itschool.study_pod.domain.introduce.service;

import com.itschool.study_pod.domain.introduce.dto.request.IntroduceRequest;
import com.itschool.study_pod.domain.introduce.dto.response.IntroduceResponse;
import com.itschool.study_pod.domain.introduce.entity.Introduce;
import com.itschool.study_pod.domain.introduce.repository.IntroduceRepository;
import com.itschool.study_pod.global.base.crud.CrudService;
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
    protected Introduce toEntity(IntroduceRequest request) {
        return Introduce.of(request);
    }
}
