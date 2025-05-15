package com.itschool.study_pod.service;

import com.itschool.study_pod.dto.request.interestedstudy.InterestedStudyRequest;
import com.itschool.study_pod.dto.response.InterestedStudyResponse;
import com.itschool.study_pod.entity.InterestedStudy;
import com.itschool.study_pod.entity.StudyGroup;
import com.itschool.study_pod.entity.User;
import com.itschool.study_pod.repository.InterestedStudyRepository;
import com.itschool.study_pod.repository.StudyGroupRepository;
import com.itschool.study_pod.repository.UserRepository;
import com.itschool.study_pod.service.base.CrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InterestedStudyService extends CrudService<InterestedStudyRequest, InterestedStudyResponse, InterestedStudy> {

    private final InterestedStudyRepository interestedStudyRepository;

    private final UserRepository userRepository;

    private final StudyGroupRepository studyGroupRepository;

    @Override
    protected JpaRepository<InterestedStudy, Long> getBaseRepository() { return interestedStudyRepository; }

    @Override
    protected InterestedStudy toEntity(InterestedStudyRequest request) {
        return InterestedStudy.of(request);
    }


    @Transactional
    private void toggleInterestedStudy(Long studyGroupId, Long userId) {// Long??
        Optional<InterestedStudy> savedInterestedStudy = interestedStudyRepository.findByStudyGroupIdAndUserId(studyGroupId, userId);


        // 관심목록에 이미 있을 경우 삭제(관심목록에서 제거)
        if (savedInterestedStudy.isPresent()) {
            interestedStudyRepository.delete(savedInterestedStudy.get());
        } else { // 스터디 그룹 관심목록에 추가

            User user = userRepository.findById(userId)
                    .orElseThrow(()-> new RuntimeException("해당 객체를 찾지 못했습니다."));

            StudyGroup studyGroup = studyGroupRepository.findById(studyGroupId)
                    .orElseThrow(()-> new RuntimeException("해당 객체를 찾지 못했습니다."));

            InterestedStudy interestedStudy = InterestedStudy.builder()
                    .user(user)
                    .studyGroup(studyGroup)
                    .build();
            interestedStudyRepository.save(interestedStudy);
        }

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
