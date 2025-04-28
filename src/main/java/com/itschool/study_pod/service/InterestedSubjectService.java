package com.itschool.study_pod.service;

import com.itschool.study_pod.dto.request.InterestedSubject.InterestedSubjectRequest;
import com.itschool.study_pod.dto.response.InterestedSubjectResponse;
import com.itschool.study_pod.entity.InterestedSubject;
import com.itschool.study_pod.entity.SubjectArea;
import com.itschool.study_pod.entity.User;
import com.itschool.study_pod.repository.InterestedSubjectRepository;
import com.itschool.study_pod.repository.SubjectAreaRepository;
import com.itschool.study_pod.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InterestedSubjectService {

    private final InterestedSubjectRepository interestedSubjectRepository;

    private final UserRepository userRepository;

    private final SubjectAreaRepository subjectAreaRepository;

    public InterestedSubjectResponse create(InterestedSubjectRequest request) {
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
    }
}
