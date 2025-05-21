package com.itschool.study_pod.global.base.crud;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itschool.study_pod.domain.imageFileUpload.service.ImageFileUploadService;
import com.itschool.study_pod.global.base.dto.Header;
import com.itschool.study_pod.global.base.account.IncludeFileUrl;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
public abstract class CrudWithFileService<Req, Res, Entity extends IncludeFileUrl<Req, Res>> extends CrudService<Req, Res, Entity> {

    @Autowired
    private ObjectMapper objectMapper;  // Jackson ObjectMapper를 사용하여 JSON을 객체로 변환

    @Autowired
    private ImageFileUploadService imageFileUploadService;

    protected abstract Class<Req> getRequestClass();

    protected abstract String getDirName();

    public Req parseStringToJson(String requestString) {
        Req request;
        try {
            request = objectMapper.readValue(requestString, getRequestClass());
        } catch (Exception e) {
            throw new RuntimeException("잘못된 JSON 형식입니다.", e);
        }
        return request;
    }

    @Override
    @Deprecated
    public Header<Res> create(Header<Req> request) {
        throw new RuntimeException("this method is deprecated");
    }

    @Override
    @Deprecated
    public Header<Res> update(Long id, Header<Req> request) {
        throw new RuntimeException("this method is deprecated");
    }

    @Transactional
    public Header<Res> create(String requestString, MultipartFile file) {
        Req request;
        String fileUrl = null;

        try {
            // 파일 업로드: 존재할 때만 수행
            if (file != null && !file.isEmpty()) {
                fileUrl = imageFileUploadService.upload(file, getDirName());
            }

            request = parseStringToJson(requestString);

            Entity entity = toEntity(request);
            entity.updateFileUrl(fileUrl);  // fileUrl이 null이어도 괜찮음

            getBaseRepository().save(entity);
            return apiResponse(entity);

        } catch (Exception e) {
            // 업로드 실패 시 이미 업로드된 파일이 있다면 삭제
            if (fileUrl != null) {
                try {
                    imageFileUploadService.deleteFile(fileUrl);
                } catch (Exception deleteEx) {
                    // 삭제 실패 로그만 남기고 넘김
                }
            }
            throw new RuntimeException("파일 업로드 오류 발생", e);
        }
    }

    @Transactional
    public Header<Res> update(Long id, String requestString, MultipartFile file) {
        Req request;
        String newFileUrl = null;

        try {
            request = parseStringToJson(requestString);

            Entity entity = getBaseRepository().findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("해당 id " + id + "에 해당하는 객체가 없습니다."));

            entity.update(request);

            // 새 파일이 존재할 때만 업데이트 수행
            if (file != null && !file.isEmpty()) {
                newFileUrl = imageFileUploadService.updateFile(entity.getFileUrl(), file);
                entity.updateFileUrl(newFileUrl);
            }

            getBaseRepository().save(entity);
            return apiResponse(entity);

        } catch (Exception e) {
            throw new RuntimeException("파일 업데이트 오류 발생", e);
        }
    }

    @Override
    public Header<Void> delete(Long id) {
        try {
            Entity entity = getBaseRepository().findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("해당 id " + id + "에 해당하는 객체가 없습니다."));

            getBaseRepository().delete(entity);

            // 파일이 존재할 경우에만 삭제
            String fileUrl = entity.getFileUrl();
            if (fileUrl != null) {
                try {
                    imageFileUploadService.deleteFile(fileUrl);
                } catch (Exception deleteEx) {
                    // 삭제 실패 로그만 남기고 넘김
                }
            }

            return Header.OK();

        } catch (Exception e) {
            throw new RuntimeException("파일 삭제 오류 발생", e);
        }
    }
}
