package com.itschool.study_pod.domain.faq.service;

import com.itschool.study_pod.domain.faq.dto.request.FaqRequest;
import com.itschool.study_pod.domain.faq.dto.response.FaqResponse;
import com.itschool.study_pod.domain.faq.entity.Faq;
import com.itschool.study_pod.domain.faq.repository.FaqRepository;
import com.itschool.study_pod.global.base.crud.CrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FaqService extends CrudService<FaqRequest, FaqResponse, Faq> {
    private final FaqRepository faqRepository;

    @Override
    protected JpaRepository<Faq, Long> getBaseRepository() {
        return faqRepository;
    }

    @Override
    protected Faq toEntity(FaqRequest request) {
        return Faq.of(request);
    }
}
