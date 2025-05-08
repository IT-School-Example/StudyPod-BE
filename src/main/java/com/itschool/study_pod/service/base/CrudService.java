package com.itschool.study_pod.service.base;


import com.itschool.study_pod.dto.Header;
import com.itschool.study_pod.dto.Pagination;
import com.itschool.study_pod.ifs.Convertible;
import com.itschool.study_pod.ifs.CrudInterface;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public abstract class CrudService<Req, Res, Entity extends Convertible<Req, Res>> implements CrudInterface<Req, Res> {

    protected abstract JpaRepository<Entity, Long> getBaseRepository();

    // 요청 DTO를 Entity로 변환
    protected abstract Entity toEntity(Req requestEntity);

    // 응답 처리(응답 DTO를 ApiResponse에 감싸서 return)
    protected Header<Res> apiResponse(Entity entity) {
        return Header.OK(entity.response());
    }

    @Override
    public Header<Res> create(Header<Req> request) {

        Entity entity = toEntity(request.getData());

        getBaseRepository().save(entity);

        return apiResponse(entity);
    }

    @Override
    public final Header<Res> read(Long id) {
        return apiResponse(getBaseRepository().findById(id)
                .orElseThrow(()-> new EntityNotFoundException(this.getClass().getSimpleName() + " : 해당 id " + id + "에 해당하는 객체가 없습니다.")));
    }


    @Transactional
    @Override
    public Header<Res> update(Long id, Header<Req> request) {

        Entity entity = getBaseRepository().findById(id)
                .orElseThrow(() -> new EntityNotFoundException(this.getClass().getSimpleName() + " : 해당 id " + id + "에 해당하는 객체가 없습니다."));

        entity.update(request.getData());

        return apiResponse(entity);
    }

    @Override
    public Header<Void> delete(Long id) {
        Entity entity = getBaseRepository().findById(id)
                .orElseThrow(() -> new EntityNotFoundException(this.getClass().getSimpleName() + " : 해당 id " + id + "에 해당하는 객체가 없습니다."));

        getBaseRepository().delete(entity);

        return Header.OK();
    }

    public Header<List<Res>> findAll() {
        List<Entity> entities = getBaseRepository().findAll();

        return responseList(entities);
    }

    protected final Header<List<Res>> responseList(List<Entity> entities) {
        List<Res> responseList = new ArrayList<>();

        for(Entity entity : entities){
            responseList.add((Res) entity.response());
        }

        return Header.OK(responseList);
    }

    public final Header<List<Res>> getPaginatedList(Pageable pageable) {
        Page<Entity> entities = getBaseRepository().findAll(pageable);

        return convertPageToList(entities);
    }

    protected final Header<List<Res>> convertPageToList(Page<Entity> entityPage) {

        List<Res> entities = entityPage.stream()
                .map(entity -> entity.response())
                .collect(Collectors.toList());

        Pagination pagination = Pagination.builder()
                .totalPages(entityPage.getTotalPages())
                .totalElements(entityPage.getTotalElements())
                .currentPage(entityPage.getNumber())
                .currentElements(entityPage.getNumberOfElements())
                .build();

        return Header.OK(entities, pagination);
    }

    /*public List<Res> createByList(List<Req> requestList) {
        List<Entity> entities = requestList.stream()
                .map(this::convertBaseEntityFromRequest)
                .collect(Collectors.toList());

        return responseList(baseRepository.saveAll(entities));
    }*/
}
