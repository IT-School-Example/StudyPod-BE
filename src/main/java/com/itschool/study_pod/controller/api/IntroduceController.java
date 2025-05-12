package com.itschool.study_pod.controller.api;

import com.itschool.study_pod.controller.base.CrudController;
import com.itschool.study_pod.dto.request.introduce.IntroduceRequest;
import com.itschool.study_pod.dto.response.IntroduceResponse;
import com.itschool.study_pod.entity.Introduce;
import com.itschool.study_pod.service.IntroduceService;
import com.itschool.study_pod.service.base.CrudService;
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
