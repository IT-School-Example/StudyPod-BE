package com.itschool.study_pod.service;


import com.itschool.study_pod.ifs.CrudInterface;
import com.itschool.study_pod.ifs.Convertible;
import com.itschool.study_pod.dto.ApiResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public abstract class CrudService<ReqDto, ResDto, Entity extends Convertible> implements CrudInterface<ReqDto, ResDto> {

    protected final JpaRepository<Entity, Long> baseRepository;



    public ResponseEntity<ApiResponse<ResDto>> create(RequestEntity<ReqDto> request) {

        Entity entity = toEntity(request.getBody());

        baseRepository.save(entity);

        return apiResponse(entity);
    }

    public final ResponseEntity<ApiResponse<ResDto>> read(Long id) {
        return apiResponse(baseRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException()));
    }


    @Transactional
    public ResponseEntity<ApiResponse<ResDto>> update(Long id, RequestEntity<ReqDto> request) {
        ReqDto requestEntity = request.getBody();

        Entity entity = baseRepository.findById(id).orElseThrow(() -> new EntityNotFoundException());

        entity.update(requestEntity);

        return apiResponse(entity);
    }


    public ResponseEntity delete(Long id) {
        Entity entity = baseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException());

        baseRepository.delete(entity);

        return ResponseEntity.ok().build();
    }


    protected final List<ResDto> responseList(List<Entity> entities) {
        List<ResDto> responseList = new ArrayList<>();

        for(Entity entity : entities){
            responseList.add((ResDto) entity.response());
        }

        return responseList;
    }

    /*public final ResponseEntity<ApiResponse<List<Res>>> getPaginatedList(Pageable pageable) {
        Page<Entity> entities = getBaseRepository().findAll(pageable);

        return convertPageToList(entities);
    }*/

    protected abstract Entity toEntity(ReqDto requestEntity);

    // protected abstract ResDto toResponseDto(Entity entity);

    protected ResponseEntity<ApiResponse<ResDto>> apiResponse(Entity entity) {
        return ResponseEntity.ok(ApiResponse.OK((ResDto) entity.response()));
    }

    /*public final ResponseEntity<ApiResponse<List<Res>>> convertPageToList(Page<Entity> entityPage) {

        List<Res> entities = entityPage.stream()
                .map(entity -> response(entity))
                .collect(Collectors.toList());

        Pagination pagination = Pagination.builder()
                .totalPages(entityPage.getTotalPages())
                .totalElements(entityPage.getTotalElements())
                .currentPage(entityPage.getNumber())
                .currentElements(entityPage.getNumberOfElements())
                .build();

        return ResponseEntity.ok(ApiResponse.OK(entities));
    }*/

    /*public List<Res> createByList(List<Req> requestList) {
        List<Entity> entities = requestList.stream()
                .map(this::convertBaseEntityFromRequest)
                .collect(Collectors.toList());

        return responseList(baseRepository.saveAll(entities));
    }*/
}
