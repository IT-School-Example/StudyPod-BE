package com.itschool.study_pod.ifs;

import com.itschool.study_pod.dto.Header;

public interface CrudInterface <Req, Res> {

    Header<Res> create(Header<Req> request);

    Header<Res> read(Long id);

    Header<Res> update(Long id, Header<Req> request);

    Header<Void> delete(Long id);

    // ResponseEntity<ApiResponse<List<Res>>> getPaginatedList(Pageable pageable);
}
