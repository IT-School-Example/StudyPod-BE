package com.itschool.study_pod.global.base.crud;

import com.itschool.study_pod.global.base.dto.FileDto;
import com.itschool.study_pod.global.base.dto.Header;
import com.itschool.study_pod.global.base.account.IncludeFileUrl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public abstract class CrudWithFileController<Req extends FileDto, Res, Entity extends IncludeFileUrl<Req, Res>> implements CrudWithFileInterface<Req, Res> {

    protected abstract CrudWithFileService<Req, Res, Entity> getBaseService();

    @Override
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "생성", description = "새로운 엔티티를 생성")
    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Header<Res> create(@RequestPart("data") String requestString,
                              @RequestPart(value = "file", required = false) MultipartFile file) {
        log.info("create: {}에서 객체 {} 생성 요청", this.getClass().getSimpleName(), requestString);
        return getBaseService().create(requestString, file);
    }

    @Override
    @Operation(summary = "조회", description = "ID로 엔티티를 조회")
    @GetMapping("{id}")
    public Header<Res> read(@PathVariable(name = "id") Long id) {
        log.info("read: {}에서 id={}로 조회 요청", this.getClass().getSimpleName(), id);
        return getBaseService().read(id);
    }

    @Override
    @Operation(summary = "수정", description = "ID로 엔티티를 업데이트")
    @PutMapping(value = "{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Header<Res> update(Long id,
                              @RequestPart("data") String requestString,
                              @RequestPart(value = "file", required = false) MultipartFile file) {
        log.info("readAll: {}에서 전체 조회 요청", this.getClass().getSimpleName());
        return getBaseService().update(id, requestString, file);
    }

    @Override
    @ResponseStatus(HttpStatus.NO_CONTENT) // 204, NO_CONTENT
    @DeleteMapping("{id}")
    @Operation(summary = "삭제", description = "ID로 엔티티를 삭제")
    public Header<Void> delete(@PathVariable(name = "id") Long id) {
        log.info("delete: {}에서 id={}인 객체 삭제 요청", this.getClass().getSimpleName(), id);
        return getBaseService().delete(id);
    }

    @GetMapping("")
    @Operation(summary = "페이지별 조회", description = "pageable로 엔티티 목록을 조회")
    public Header<List<Res>> getPaginatedList(
            // Swagger 문서에 pageable 파라미터 예시 추가
            @Parameter(name = "pageable", description = "페이징 설정 (page, size, sort)", example = "{\n\"page\":0,\n\"size\":10,\n\"sort\":[\"id,asc\"]\n}")
            @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {

        log.info("getPaginatedList: {}에서 pageable={}인 객체들 조회 요청", this.getClass().getSimpleName(), pageable);
        return getBaseService().getPaginatedList(pageable);
    }

    @Deprecated
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "생성 - json 확인용", description = "새로운 엔티티를 생성")
    @PostMapping("/test")
    public Header<Res> create(Header<Req> request) {
        throw new RuntimeException("this method is deprecated");
    }

    @Deprecated
    @Operation(summary = "수정  - json 확인용", description = "ID로 엔티티를 업데이트")
    @PutMapping("/test")
    public Header<Res> update(Long id, Header<Req> request) {
        throw new RuntimeException("this method is deprecated");
    }
}
