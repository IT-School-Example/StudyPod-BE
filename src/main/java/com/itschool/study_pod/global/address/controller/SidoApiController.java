package com.itschool.study_pod.global.address.controller;

import com.itschool.study_pod.global.base.dto.Header;
import com.itschool.study_pod.global.address.dto.request.SidoRequest;
import com.itschool.study_pod.global.address.dto.response.SidoResponse;
import com.itschool.study_pod.global.address.service.SidoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "시도", description = "시도 주소 API")
@RequestMapping("/api/sido")
public class SidoApiController {

    private final SidoService sidoService;

    @GetMapping("{id}")
    @PreAuthorize("isAuthenticated()")
    @Operation(
            summary = "단일 시도 조회",
            description = "sidoCd (예: 11)를 기준으로 해당 시도(Sido)의 상세 정보를 조회합니다. 인증된 사용자만 호출할 수 있습니다."
    )
    public Header<SidoResponse> read(@PathVariable(name = "id") String id) {
        log.info("read: {}에서 id={}로 조회 요청", this.getClass().getSimpleName(), id);
        return sidoService.read(id);
    }


    @GetMapping("")
    @PreAuthorize("isAuthenticated()")
    @Operation(
            summary = "전체 시도 목록 조회",
            description = "등록된 전체 시도(Sido) 목록을 조회합니다. 인증된 사용자만 호출할 수 있습니다."
    )
    public Header<List<SidoResponse>> readAll() {
        log.info("readAll: 전체 시도 목록 조회 요청");
        return sidoService.readAll();
    }

    @PostMapping("")
    @Deprecated
    @PreAuthorize("denyAll()")
    public Header<SidoResponse> create(RequestEntity<SidoRequest> request) {
        throw new RuntimeException("Sgg 생성 접근 불가");
    }

    @PutMapping("{id}")
    @Deprecated
    @PreAuthorize("denyAll()")
    public Header<SidoResponse> update(Long id, RequestEntity<SidoRequest> request) {
        throw new RuntimeException("Sgg 수정 접근 불가");
    }

    @DeleteMapping("{id}")
    @Deprecated
    @PreAuthorize("denyAll()")
    public Header<Void> delete(Long id) {
        throw new RuntimeException("Sgg 삭제 접근 불가");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Header> handleException(Exception e) {
        log.error("Exception Occurred: ", e);
        String errorMessage = e.getClass().getSimpleName() + " : " + e.getMessage();
        Header errorResponse = Header.ERROR(errorMessage);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}
