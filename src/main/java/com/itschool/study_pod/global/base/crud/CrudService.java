package com.itschool.study_pod.global.base.crud;


import com.itschool.study_pod.global.base.account.Account;
import com.itschool.study_pod.global.base.dto.Header;
import com.itschool.study_pod.global.base.dto.Pagination;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;

import java.security.Security;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public abstract class CrudService<Req, Res, Entity extends Convertible<Req, Res>> implements CrudInterface<Req, Res> {

    protected abstract JpaRepository<Entity, Long> getBaseRepository();

    // 요청 DTO를 Entity로 변환
    protected abstract Entity toEntity(Req request);

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
    public Header<Res> read(Long id) {
        return apiResponse(getBaseRepository().findById(id)
                .orElseThrow(()-> new EntityNotFoundException("해당 id " + id + "에 해당하는 객체가 없습니다.")));
    }


    @Transactional
    @Override
    public Header<Res> update(Long id, Header<Req> request) {

        Entity entity = getBaseRepository().findById(id)
                .orElseThrow(() -> new EntityNotFoundException("해당 id " + id + "에 해당하는 객체가 없습니다."));

        entity.update(request.getData());

        return apiResponse(entity);
    }

    @Override
    public Header<Void> delete(Long id) {
        Entity entity = getBaseRepository().findById(id)
                .orElseThrow(() -> new EntityNotFoundException("해당 id " + id + "에 해당하는 객체가 없습니다."));

        getBaseRepository().delete(entity);

        return Header.OK();
    }

    public Header<List<Res>> findAll() {
        List<Entity> entities = getBaseRepository().findAll();

        return responseList(entities);
    }

    public final Header<List<Res>> getPaginatedList(Pageable pageable) {
        Page<Entity> entities = getBaseRepository().findAll(pageable);

        return convertPageToList(entities);
    }

    protected final Header<List<Res>> responseList(List<? extends Convertible<?, Res>> entities) {
        List<Res> responseList = entities.stream()
                .map(Convertible::response)
                .collect(Collectors.toList());

        return Header.OK(responseList);
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
