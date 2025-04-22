package com.itschool.study_pod.ifs;

import com.itschool.study_pod.dto.ApiResponse;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;

public interface CrudInterface <ReqDto, ResDto> {

    ResponseEntity<ApiResponse<ResDto>> create(RequestEntity<ReqDto> request);

    ResponseEntity<ApiResponse<ResDto>> read(Long id);

    ResponseEntity<ApiResponse<ResDto>> update(Long id, RequestEntity<ReqDto>  request);

    ResponseEntity delete(Long id);

    // ResponseEntity<ApiResponse<List<Res>>> getPaginatedList(Pageable pageable);
}
