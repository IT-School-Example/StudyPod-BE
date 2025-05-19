package com.itschool.study_pod.domain.subjectarea.service;

import com.itschool.study_pod.global.base.crud.CrudService;
import com.itschool.study_pod.domain.subjectarea.repository.SubjectAreaRepository;
import com.itschool.study_pod.domain.subjectarea.dto.request.SubjectAreaRequest;
import com.itschool.study_pod.domain.subjectarea.dto.response.SubjectAreaResponse;
import com.itschool.study_pod.domain.subjectarea.entity.SubjectArea;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SubjectAreaService extends CrudService<SubjectAreaRequest, SubjectAreaResponse, SubjectArea> {

    private final SubjectAreaRepository subjectAreaRepository;

    @Override
    protected JpaRepository<SubjectArea, Long> getBaseRepository() {
        return subjectAreaRepository;
    }

    @Override
    protected SubjectArea toEntity(SubjectAreaRequest request) {
        return SubjectArea.of(request);
    }


    /*public SubjectAreaResponse create(SubjectAreaRequest request) {
        return subjectAreaRepository.save(SubjectArea.of(request))
                .response();
    }

    public SubjectAreaResponse read(Long id) {
        return subjectAreaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException())
                .response();
    }

    @Transactional
    public SubjectAreaResponse update(Long id, SubjectAreaRequest subjectAreaRequest) {
        SubjectArea entity = subjectAreaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException());

        entity.update(subjectAreaRequest);

        return entity.response();
    }

    public void delete(Long id) {
//        interestedSubjectRepository.deleteById(id);

        SubjectArea findEntity = subjectAreaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException());

        subjectAreaRepository.delete(findEntity);
    }*/
}
