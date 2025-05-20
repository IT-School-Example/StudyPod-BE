package com.itschool.study_pod.domain.introduce.controller;

import com.itschool.study_pod.global.base.crud.CrudController;
import com.itschool.study_pod.domain.introduce.dto.request.IntroduceRequest;
import com.itschool.study_pod.domain.introduce.dto.response.IntroduceResponse;
import com.itschool.study_pod.domain.introduce.service.IntroduceService;
import com.itschool.study_pod.domain.introduce.entity.Introduce;
import com.itschool.study_pod.global.base.crud.CrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/introduce")
public class IntroduceController extends CrudController<IntroduceRequest, IntroduceResponse, Introduce> {

    private final IntroduceService introduceService;

    @Override
    protected CrudService<IntroduceRequest, IntroduceResponse, Introduce> getBaseService() {
        return introduceService;
    }
}
