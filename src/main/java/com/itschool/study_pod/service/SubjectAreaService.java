package com.itschool.study_pod.service;

import com.itschool.study_pod.dto.request.SubjectAreaRequest;
import com.itschool.study_pod.dto.response.SubjectAreaResponse;
import com.itschool.study_pod.entity.SubjectArea;
import com.itschool.study_pod.repository.SubjectAreaRepository;
import com.itschool.study_pod.service.base.CrudService;
import org.springframework.stereotype.Service;

@Service
public class SubjectAreaService extends CrudService<SubjectAreaRequest, SubjectAreaResponse, SubjectArea> {


    public SubjectAreaService(SubjectAreaRepository baseRepository) {
        super(baseRepository);
    }

    @Override
    protected SubjectArea toEntity(SubjectAreaRequest requestEntity) {
        return SubjectArea.of(requestEntity);
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
