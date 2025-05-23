package com.itschool.study_pod.global.base.crud;

import com.itschool.study_pod.global.base.dto.Header;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
public abstract class CrudController<Req, Res, Entity extends Convertible<Req, Res>> implements CrudInterface<Req, Res> {

    protected abstract CrudService<Req, Res, Entity> getBaseService();


    @Override
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "생성", description = "새로운 엔티티를 생성")
    @PostMapping("")
    public Header<Res> create(@RequestBody @Valid Header<Req> request) {
        log.info("create: {}에서 객체 {} 생성 요청", this.getClass().getSimpleName(), request);
        return getBaseService().create(request);
    }

    @Override
    @Operation(summary = "조회", description = "ID로 엔티티를 조회")
    @GetMapping("{id}")
    public Header<Res> read(@PathVariable(name = "id") Long id) {
        log.info("read: {}에서 id={}로 조회 요청", this.getClass().getSimpleName(), id);
        return getBaseService().read(id);
    }

    // 페이지 단위 조회
    @GetMapping("")
    @Operation(summary = "페이지별 조회", description = "pageable로 엔티티 목록을 조회")
    public Header<List<Res>> getPaginatedList(
            // Swagger 문서에 pageable 파라미터 예시 추가
            @Parameter(name = "pageable", description = "페이징 설정 (page, size, sort)", example = "{\n\"page\":0,\n\"size\":10,\n\"sort\":[\"id,asc\"]\n}")
            @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {

        log.info("getPaginatedList: {}에서 pageable={}인 객체들 조회 요청", this.getClass().getSimpleName(), pageable);
        return getBaseService().getPaginatedList(pageable);
    }

    @Override
    @Operation(summary = "수정", description = "ID로 엔티티를 업데이트")
    @PutMapping("{id}")
    public Header<Res> update(@PathVariable(name = "id") Long id,
                              @RequestBody @Valid Header<Req> request) {
        log.info("readAll: {}에서 전체 조회 요청", this.getClass().getSimpleName());
        return getBaseService().update(id, request);
    }

    @Override
    @ResponseStatus(HttpStatus.NO_CONTENT) // 204, NO_CONTENT
    @DeleteMapping("{id}")
    @Operation(summary = "삭제", description = "ID로 엔티티를 삭제")
    public Header<Void> delete(@PathVariable(name = "id") Long id) {
        log.info("delete: {}에서 id={}인 객체 삭제 요청", this.getClass().getSimpleName(), id);
        return getBaseService().delete(id);
    }

    /*@GetMapping("all")
    @Operation(summary = "전체 조회(임시)", description = "임시로 전체 조회 가능하도록 열어둠")
    public Header<List<Res>> findAll() {
        return getBaseService().findAll();
    }*/

    // 요청 DTO 클래스 타입 반환용 (리플렉션 등에서 활용 가능)
    // protected abstract Class<CreateReq> getRequestClass();

    /*protected Long getCurrentAccountId() {
        // 인증된 사용자 객체
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
        if(principal instanceof Account) {
            Account account = (Account) principal;
            return account.getId();
        }

        throw new IllegalArgumentException("인증된 사용자 정보를 확인할 수 없습니다.");
    }*/

    // 전역 예외 핸들링용 핸들러 (Controller 내에서 발생하는 예외 처리)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Header> handleException(Exception e) {
        log.error("Exception Occurred: ", e);
        String errorMessage = e.getClass().getSimpleName() + " : " + e.getMessage();

        if (e instanceof EntityNotFoundException) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Header.ERROR(errorMessage));
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Header.ERROR(errorMessage));
    }

}