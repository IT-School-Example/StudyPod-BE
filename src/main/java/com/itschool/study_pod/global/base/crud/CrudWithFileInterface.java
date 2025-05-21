package com.itschool.study_pod.global.base.crud;

import com.itschool.study_pod.global.base.dto.Header;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CrudWithFileInterface<Req, Res> {
    Header<Res> create(String requestString, MultipartFile file);

    Header<Res> read(Long id);

    Header<Res> update(Long id, String requestString, MultipartFile file);

    Header<Void> delete(Long id);

    Header<List<Res>> getPaginatedList(Pageable pageable);

}
