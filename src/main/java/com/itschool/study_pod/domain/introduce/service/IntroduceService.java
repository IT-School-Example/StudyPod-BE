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
import org.springframework.transaction.annotation.Transactional;

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

    public Introduce findById(Long id) {
        return introduceRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("소개글을 찾을 수 없습니다. ID=" + id));
    }

    @Transactional
    public Introduce togglePosted(Long id) {
        Introduce introduce = findById(id);
        introduce.setPosted(!introduce.isPosted());
        return introduceRepository.save(introduce);
    }

    public Header<IntroduceResponse> getByStudyGroupId(Long studyGroupId) {
        Introduce introduce = introduceRepository.findByStudyGroupId(studyGroupId)
                .orElseThrow(() -> new NoSuchElementException("해당 스터디 그룹의 소개글이 존재하지 않습니다."));
        return Header.OK(introduce.response());
    }

    @Transactional
    public Header<IntroduceResponse> updateByStudyGroupId(IntroduceRequest request) {
        Long studyGroupId = request.getStudyGroup().getId();

        Introduce introduce = introduceRepository.findByStudyGroupId(studyGroupId)
                .orElseThrow(() -> new NoSuchElementException("해당 스터디 그룹의 소개글이 존재하지 않습니다."));

        introduce.update(request); // 엔티티 자체 update 호출
        Introduce updated = introduceRepository.save(introduce);

        return Header.OK(updated.response());
    }

    @Transactional
    public Introduce togglePostedByStudyId(Long studyId) {
        Introduce introduce = introduceRepository.findByStudyGroupId(studyId)
                .orElseThrow(() -> new NoSuchElementException("해당 스터디 그룹의 소개글이 존재하지 않습니다."));
        introduce.setPosted(!introduce.isPosted());
        return introduceRepository.save(introduce);
    }

    public boolean existsByStudyGroupId(Long studyGroupId) {
        return introduceRepository.findByStudyGroupId(studyGroupId).isPresent();
    }

    public boolean isPostedByStudyGroupId(Long studyGroupId) {
        return introduceRepository.findByStudyGroupId(studyGroupId)
                .map(Introduce::isPosted)
                .orElse(false);
    }
}
