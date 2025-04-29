package com.itschool.study_pod.service.base;


import com.itschool.study_pod.ifs.CrudInterface;
import com.itschool.study_pod.ifs.Convertible;
import com.itschool.study_pod.dto.Header;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
public abstract class CrudService<Req, Res, Entity extends Convertible<Req, Res>> implements CrudInterface<Req, Res> {

    protected abstract JpaRepository<Entity, Long> getBaseRepository();

    // 요청 DTO를 Entity로 변환
    protected abstract Entity toEntity(Req requestEntity);

    // 응답 처리(응답 DTO를 ApiResponse에 감싸서 return)
    protected Header<Res> apiResponse(Entity entity) {
        return Header.OK(entity.response());
    }

    public Header<Res> create(Header<Req> request) {

        Entity entity = toEntity(request.getData());

        getBaseRepository().save(entity);

        return apiResponse(entity);
    }

    public final Header<Res> read(Long id) {
        return apiResponse(getBaseRepository().findById(id)
                .orElseThrow(()-> new EntityNotFoundException("해당 id " + id + "에 해당하는 객체가 없습니다.")));
    }


    @Transactional
    public Header<Res> update(Long id, Header<Req> request) {

        Entity entity = getBaseRepository().findById(id)
                .orElseThrow(() -> new EntityNotFoundException("해당 id " + id + "에 해당하는 객체가 없습니다."));

        entity.update(request.getData());

        return apiResponse(entity);
    }


    public Header<Void> delete(Long id) {
        Entity entity = getBaseRepository().findById(id)
                .orElseThrow(() -> new EntityNotFoundException("해당 id " + id + "에 해당하는 객체가 없습니다."));

        getBaseRepository().delete(entity);

        return Header.OK();
    }

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

    /*protected final List<Res> responseList(List<Entity> entities) {
        List<Res> responseList = new ArrayList<>();

        for(Entity entity : entities){
            responseList.add((Res) entity.response());
        }

        return responseList;
    }*/

    /*public List<Res> createByList(List<Req> requestList) {
        List<Entity> entities = requestList.stream()
                .map(this::convertBaseEntityFromRequest)
                .collect(Collectors.toList());

        return responseList(baseRepository.saveAll(entities));
    }*/
}
