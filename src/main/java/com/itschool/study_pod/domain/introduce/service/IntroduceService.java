package com.itschool.study_pod.domain.introduce.service;

import com.itschool.study_pod.domain.introduce.dto.request.IntroduceRequest;
import com.itschool.study_pod.domain.introduce.dto.response.IntroduceResponse;
import com.itschool.study_pod.domain.introduce.entity.Introduce;
import com.itschool.study_pod.domain.introduce.repository.IntroduceRepository;
import com.itschool.study_pod.global.base.crud.CrudService;
import com.itschool.study_pod.global.base.dto.Header;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

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

    public Header<IntroduceResponse> findByStudyGroupId(Long studyGroupId) {
        Optional<Introduce> optional = Optional.ofNullable(introduceRepository.findByStudyGroupId(studyGroupId)
                .orElse(null));

        if (optional.isEmpty()) {
            return Header.ERROR("해당 스터디 그룹의 소개글이 존재하지 않습니다.");
        }

        Introduce introduce = optional.get();
        if (!introduce.isPosted()) {
            return Header.ERROR("아직 개시되지 않은 소개글입니다.");
        }
        return Header.OK(introduce.response());
    }

}
