package com.itschool.study_pod.controller.api;

import com.itschool.study_pod.controller.base.CrudController;
import com.itschool.study_pod.dto.request.FaqRequest;
import com.itschool.study_pod.dto.response.FaqResponse;
import com.itschool.study_pod.entity.Faq;
import com.itschool.study_pod.service.FaqService;
import com.itschool.study_pod.service.base.CrudService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "자주 묻는 질문", description = "자주 묻는 질문 API")
@RequestMapping("/api/faqs")
public class FaqController extends CrudController<FaqRequest, FaqResponse, Faq> {

    private final FaqService faqService;

    @Override
    protected CrudService<FaqRequest, FaqResponse, Faq> getBaseService() {
        return faqService;
    }
}
