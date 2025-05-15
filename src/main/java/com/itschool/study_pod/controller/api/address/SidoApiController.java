package com.itschool.study_pod.controller.api.address;

import com.itschool.study_pod.dto.Header;
import com.itschool.study_pod.dto.request.address.SidoRequest;
import com.itschool.study_pod.dto.response.address.SidoResponse;
import com.itschool.study_pod.service.address.SidoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "시도", description = "시도 주소 API")
@RequestMapping("/api/sido")
public class SidoApiController {

    private final SidoService sidoService;

    @GetMapping("{id}")
    @PreAuthorize("isAuthenticated()")
    public Header<SidoResponse> read(@PathVariable(name = "id") String id) {
        log.info("read: {}에서 id={}로 조회 요청", this.getClass().getSimpleName(), id);
        return sidoService.read(id);
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
