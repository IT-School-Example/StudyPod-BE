package com.itschool.study_pod.service.base;


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

@RequiredArgsConstructor
public abstract class CrudService<Req, Res, Entity extends Convertible<Req, Res>> implements CrudInterface<Req, Res> {

    protected abstract JpaRepository<Entity, Long> getBaseRepository();

    // 요청 DTO를 Entity로 변환
    protected abstract Entity toEntity(Req requestEntity);

    // 응답 처리(응답 DTO를 ApiResponse에 감싸서 return)
    protected ApiResponse<Res> apiResponse(Entity entity) {
        return ApiResponse.OK(entity.response());
    }

    public ApiResponse<Res> create(RequestEntity<Req> request) {

        Entity entity = toEntity(request.getBody());

        getBaseRepository().save(entity);

        return apiResponse(entity);
    }

    public final ApiResponse<Res> read(Long id) {
        return apiResponse(getBaseRepository().findById(id)
                .orElseThrow(()-> new EntityNotFoundException()));
    }


    @Transactional
    public ApiResponse<Res> update(Long id, RequestEntity<Req> request) {
        Req requestEntity = request.getBody();

        Entity entity = getBaseRepository().findById(id).orElseThrow(() -> new EntityNotFoundException());

        entity.update(requestEntity);

        return apiResponse(entity);
    }


    public ApiResponse<Void> delete(Long id) {
        Entity entity = getBaseRepository().findById(id)
                .orElseThrow(() -> new EntityNotFoundException());

        getBaseRepository().delete(entity);

        return ApiResponse.OK();
    }



    /*protected final List<Res> responseList(List<Entity> entities) {
        List<Res> responseList = new ArrayList<>();

        for(Entity entity : entities){
            responseList.add((Res) entity.response());
        }

        return responseList;
    }*/

    /*public final ResponseEntity<ApiResponse<List<Res>>> getPaginatedList(Pageable pageable) {
        Page<Entity> entities = getBaseRepository().findAll(pageable);

        return convertPageToList(entities);
    }*/

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
