package com.itschool.study_pod.service;

import com.itschool.study_pod.dto.request.InterestedStudyRequest;
import com.itschool.study_pod.dto.request.InterestedSubjectRequest;
import com.itschool.study_pod.dto.response.InterestedStudyResponse;
import com.itschool.study_pod.entity.InterestedStudy;
import com.itschool.study_pod.entity.InterestedSubject;
import com.itschool.study_pod.repository.InterestedStudyRepository;
import com.itschool.study_pod.repository.StudyGroupRepository;
import com.itschool.study_pod.repository.UserRepository;
import com.itschool.study_pod.service.base.CrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InterestedStudyService extends CrudService<InterestedStudyRequest, InterestedStudyResponse, InterestedStudy> {

    private final InterestedStudyRepository interestedStudyRepository;

    private final UserRepository userRepository;

    private final StudyGroupRepository studyGroupRepository;

    @Override
    protected JpaRepository<InterestedStudy, Long> getBaseRepository() { return interestedStudyRepository; }

    @Override
    protected InterestedStudy toEntity(InterestedStudyRequest requestEntity) {
        return InterestedStudy.of(requestEntity);
    }

    /*public InterestedStudyResponse create(InterestedStudyRequest request) {
        return interestedStudyRepository.save(InterestedStudy.of(request))
                .response();
    }

    // 조회(Read) 테스트
    public InterestedStudyResponse read(Long id) {
        return interestedStudyRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException())
                .response();
    }

    // 수정(Update) 테스트
    @Transactional
    public InterestedStudyResponse update(Long id, InterestedStudyRequest interestedStudyRequest) {
        InterestedStudy entity = interestedStudyRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException());

        entity.update(interestedStudyRequest);

        return entity.response();
    }

    // 삭제(Delete) 테스트
    public void delete(Long id) {
//        interestedStudyRepository.deleteById(id);

        InterestedStudy findEntity = interestedStudyRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException());

        interestedStudyRepository.delete(findEntity);
    }*/

}
