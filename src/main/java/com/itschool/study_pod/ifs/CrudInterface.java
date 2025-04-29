package com.itschool.study_pod.ifs;

import com.itschool.study_pod.dto.ApiResponse;
import org.springframework.http.RequestEntity;

public interface CrudInterface <Req, Res> {

    ApiResponse<Res> create(RequestEntity<Req> request);

    ApiResponse<Res> read(Long id);

    ApiResponse<Res> update(Long id, RequestEntity<Req>  request);

    ApiResponse<Void> delete(Long id);

    // ResponseEntity<ApiResponse<List<Res>>> getPaginatedList(Pageable pageable);
}
