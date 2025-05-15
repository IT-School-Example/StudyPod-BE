package com.itschool.study_pod.service;

import com.itschool.study_pod.dto.request.faq.FaqRequest;
import com.itschool.study_pod.dto.response.FaqResponse;
import com.itschool.study_pod.entity.Faq;
import com.itschool.study_pod.repository.FaqRepository;
import com.itschool.study_pod.service.base.CrudService;
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
