package com.itschool.study_pod.domain.interestedsubject.service;

import com.itschool.study_pod.domain.interestedsubject.repository.InterestedSubjectRepository;
import com.itschool.study_pod.domain.interestedsubject.dto.request.InterestedSubjectRequest;
import com.itschool.study_pod.domain.interestedsubject.dto.response.InterestedSubjectResponse;
import com.itschool.study_pod.domain.interestedsubject.entity.InterestedSubject;
import com.itschool.study_pod.domain.subjectarea.repository.SubjectAreaRepository;
import com.itschool.study_pod.domain.user.repository.UserRepository;
import com.itschool.study_pod.global.base.crud.CrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InterestedSubjectService extends CrudService<InterestedSubjectRequest, InterestedSubjectResponse, InterestedSubject> {

    private final InterestedSubjectRepository interestedSubjectRepository;

    private final UserRepository userRepository;

    private final SubjectAreaRepository subjectAreaRepository;


    @Override
    protected JpaRepository<InterestedSubject, Long> getBaseRepository() {
        return interestedSubjectRepository;
    }

    @Override
    protected InterestedSubject toEntity(InterestedSubjectRequest request) {
        return InterestedSubject.of(request);
    }

    /*public InterestedSubjectResponse create(InterestedSubjectRequest request) {
        return interestedSubjectRepository.save(InterestedSubject.of(request))
                .response();
    }

    // 조회(Read) 테스트
    public InterestedSubjectResponse read(Long id) {
        return interestedSubjectRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException())
                .response();
    }

    // 수정(Update) 테스트
    @Transactional
    public InterestedSubjectResponse update(Long id, InterestedSubjectRequest interestedSubjectRequest) {
        InterestedSubject entity = interestedSubjectRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException());

        entity.update(interestedSubjectRequest);

        return entity.response();
    }

    // 삭제(Delete) 테스트
    public void delete(Long id) {
//        interestedSubjectRepository.deleteById(id);

        InterestedSubject findEntity = interestedSubjectRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException());

        interestedSubjectRepository.delete(findEntity);
    }*/
}
