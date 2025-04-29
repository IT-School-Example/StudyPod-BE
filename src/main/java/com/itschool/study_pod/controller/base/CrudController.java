package com.itschool.study_pod.controller.base;

import com.itschool.study_pod.ifs.CrudInterface;
import com.itschool.study_pod.ifs.Convertible;
import com.itschool.study_pod.dto.Header;
import com.itschool.study_pod.service.base.CrudService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
public abstract class CrudController<Req, Res, Entity extends Convertible<Req, Res>> implements CrudInterface<Req, Res> {

    protected abstract CrudService<Req, Res, Entity> getBaseService();


    @Override
    // Swagger 문서 생성을 위한 어노테이션 예시
    // @Operation(summary = "생성", description = "새로운 엔티티를 생성")
    @PostMapping("")
    public Header<Res> create(@RequestBody /*@Valid*/ Header<Req> request) {
        log.info("create: {}에서 객체 {} 생성 요청", this.getClass().getSimpleName(), request);
        return getBaseService().create(request);
    }

    @Override
    // @Operation(summary = "읽기", description = "ID로 엔티티를 조회")
    @GetMapping("{id}")
    public Header<Res> read(@PathVariable(name = "id") Long id) {
        log.info("read: {}에서 id={}로 조회 요청", this.getClass().getSimpleName(), id);
        return getBaseService().read(id);
    }

    @Override
    // @Operation(summary = "수정", description = "ID로 엔티티를 업데이트")
    @PutMapping("{id}")
    public Header<Res> update(@PathVariable(name = "id") Long id,
                              @RequestBody /*@Valid*/ Header<Req> request) {
        log.info("readAll: {}에서 전체 조회 요청", this.getClass().getSimpleName());
        return getBaseService().update(id, request);
    }

    @Override
    @DeleteMapping("{id}")
    // @Operation(summary = "삭제", description = "ID로 엔티티를 삭제")
    public Header<Void> delete(@PathVariable(name = "id") Long id) {
        log.info("delete: {}에서 id={}인 객체 삭제 요청", this.getClass().getSimpleName(), id);
        return getBaseService().delete(id);
    }

    /*
    // 페이지 단위 조회 (페이징 기능 추가용)
    @GetMapping("")
    // Swagger 문서 생성을 위한 어노테이션 예시
    // @Operation(summary = "페이지별 조회", description = "pageable로 엔티티 목록을 조회")
    public ResponseEntity<ApiResponse<List<Res>>> getPaginatedList(
            // Swagger 문서에 pageable 파라미터 예시 추가
            // @Parameter(name = "pageable", description = "페이징 설정 (page, size, sort)", example = "{\n\"page\":0,\n\"size\":10,\n\"sort\":[\"id,asc\"]\n}")
            @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {

        log.info("{}","{}","getPaginatedList: ", pageable);
        return baseService.getPaginatedList(pageable);
    }
    */

    // 요청 DTO 클래스 타입 반환용 (리플렉션 등에서 활용 가능)
    // protected abstract Class<CreateReq> getRequestClass();

    // 전역 예외 핸들링용 핸들러 (Controller 내에서 발생하는 예외 처리)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Header> handleException(Exception e) {
        // 예외 정보 로그
        log.error("Exception Occurred: ", e);

        // 클라이언트에게 반환할 메시지 생성
        String errorMessage = e.getClass().getSimpleName() + " : " + e.getMessage();

        // 에러 응답 본문 생성
        Header errorResponse = Header.ERROR(errorMessage);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(errorResponse);
    }
}