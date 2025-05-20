package com.itschool.study_pod.domain.imageFileUpload.controller;

import com.itschool.study_pod.domain.imageFileUpload.service.ImageFileUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/images")
public class ImageFileUploadController {

    private final ImageFileUploadService imageFileUploadService;

    // 파일 업로드
    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("dirName") String dirName,
                                             @RequestPart("file") MultipartFile file) {
        try {
            String fileUrl = imageFileUploadService.upload(file, dirName);
            return new ResponseEntity<>(fileUrl, HttpStatus.CREATED); // 성공적으로 파일 업로드 시 URL 반환
        } catch (IOException | S3Exception e) {
            return new ResponseEntity<>("파일 업로드에 실패했습니다. 다시 시도해 주세요.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 파일 수정 (기존 파일을 삭제하고 새 파일을 업로드)
    @PutMapping("/update")
    public ResponseEntity<String> updateFile(@RequestParam("fileUrl") String fileUrl,
                                             @RequestPart("newFile") MultipartFile newFile) {
        try {
            String updatedFileUrl = imageFileUploadService.updateFile(fileUrl, newFile);
            return new ResponseEntity<>(updatedFileUrl, HttpStatus.OK); // 수정된 파일의 URL 반환
        } catch (IOException | S3Exception e) {
            return new ResponseEntity<>("파일 수정에 실패했습니다. 다시 시도해 주세요.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 파일 삭제
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteFile(@RequestPart("fileUrl") String fileUrl) {
        try {
            imageFileUploadService.deleteFile(fileUrl);
            return new ResponseEntity<>("파일이 성공적으로 삭제되었습니다.", HttpStatus.NO_CONTENT); // 삭제 성공 메시지
        } catch (S3Exception e) {
            return new ResponseEntity<>("파일 삭제에 실패했습니다. 다시 시도해 주세요.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
