package com.itschool.study_pod.controller.base;

import com.itschool.study_pod.ifs.CrudInterface;
import com.itschool.study_pod.ifs.Updatable;
import com.itschool.study_pod.dto.ApiResponse;
import com.itschool.study_pod.service.CrudService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@Component
@RequiredArgsConstructor
@Slf4j
public abstract class CrudController<ReqDto, ResDto, Entity extends Updatable> implements CrudInterface<ReqDto, ResDto> {

    protected final CrudService<ReqDto, ResDto, Entity> baseService;

    // 요청 DTO 클래스 타입 반환용 (리플렉션 등에서 활용 가능)
    protected abstract Class<ReqDto> getRequestClass();

    @Override
    // Swagger 문서 생성을 위한 어노테이션 예시
    // @Operation(summary = "생성", description = "새로운 엔티티를 생성")
    @PostMapping("")
    public ResponseEntity<ApiResponse<ResDto>> create(@RequestBody /*@Valid*/ RequestEntity<ReqDto> request) {
        log.info("{}","{}", "create : ", request);
        return baseService.create(request);
    }

    @Override
    // @Operation(summary = "읽기", description = "ID로 엔티티를 조회")
    @GetMapping("{id}")
    public ResponseEntity<ApiResponse<ResDto>> read(@PathVariable(name = "id") Long id) {
        log.info("{}","{}","read: ", id);
        return baseService.read(id);
    }

    @Override
    // @Operation(summary = "수정", description = "ID로 엔티티를 업데이트")
    @PutMapping("{id}")
    public ResponseEntity<ApiResponse<ResDto>> update(@PathVariable(name = "id") Long id,
                                                      @RequestBody /*@Valid*/ RequestEntity<ReqDto> request) {
        log.info("{}","{}","{}", "update: ", id, request);
        return baseService.update(id, request);
    }

    @Override
    @DeleteMapping("{id}")
    // @Operation(summary = "삭제", description = "ID로 엔티티를 삭제")
    public ResponseEntity delete(@PathVariable(name = "id") Long id) {
        log.info("{}","{}","delete: ", id);
        return baseService.delete(id);
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

    /*
    // 전역 예외 핸들링용 핸들러 (Controller 내에서 발생하는 예외 처리)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleException(Exception e) {
        log.error(e.getClass().getSimpleName() + " : " + e.getMessage() + "\n" + e.getCause() + ExceptionUtils.getStackTrace(e));
        return Header.ERROR(e.getClass().getSimpleName() + " : " + e.getCause());
    }
    */
}