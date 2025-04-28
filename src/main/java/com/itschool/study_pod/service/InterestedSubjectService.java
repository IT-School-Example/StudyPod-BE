package com.itschool.study_pod.service;

import com.itschool.study_pod.dto.request.InterestedSubjectRequest;
import com.itschool.study_pod.dto.response.InterestedSubjectResponse;
import com.itschool.study_pod.entity.InterestedSubject;
import com.itschool.study_pod.repository.InterestedSubjectRepository;
import com.itschool.study_pod.repository.SubjectAreaRepository;
import com.itschool.study_pod.repository.UserRepository;
import com.itschool.study_pod.service.base.CrudService;
import org.springframework.stereotype.Service;

@Service
public class InterestedSubjectService extends CrudService<InterestedSubjectRequest, InterestedSubjectResponse, InterestedSubject> {

    private final UserRepository userRepository;

    private final SubjectAreaRepository subjectAreaRepository;


    public InterestedSubjectService(InterestedSubjectRepository baseRepository, UserRepository userRepository, SubjectAreaRepository subjectAreaRepository) {
        super(baseRepository);
        this.userRepository = userRepository;
        this.subjectAreaRepository = subjectAreaRepository;
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


//            InterestedSubject interestedSubject = interestedSubjectRepository.findById(request.getInterestedSubjectId())
//                    .orElseThrow(() -> new EntityNotFoundException());
//
//            User user = userRepository.findById(request.getUserId())
//                    .orElseThrow(() -> new EntityNotFoundException());
//
//            SubjectArea subjectArea = subjectAreaRepository.findById(request.get())
//                    .orElseThrow(() -> new EntityNotFoundException());
//
//            return subjectAreaRepository.save(SubjectArea.of(request, interestedSubject, user ,subjectArea))
//                    .response();

        interestedSubjectRepository.delete(findEntity);
    }*/
}
