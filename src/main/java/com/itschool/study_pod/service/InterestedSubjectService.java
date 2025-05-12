package com.itschool.study_pod.service;

import com.itschool.study_pod.dto.request.interestedsubject.InterestedSubjectRequest;
import com.itschool.study_pod.dto.response.InterestedSubjectResponse;
import com.itschool.study_pod.entity.InterestedSubject;
import com.itschool.study_pod.repository.InterestedSubjectRepository;
import com.itschool.study_pod.repository.SubjectAreaRepository;
import com.itschool.study_pod.repository.UserRepository;
import com.itschool.study_pod.service.base.CrudService;
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
    protected InterestedSubject toEntity(InterestedSubjectRequest requestEntity) {
        return InterestedSubject.of(requestEntity);
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
