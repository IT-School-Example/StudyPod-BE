package com.itschool.study_pod.service;

import com.itschool.study_pod.common.FileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageFileUploadService {
    private final S3Client s3Client;

    @Value("${spring.cloud.aws.s3.bucket}")
    private String bucketName;
    
    // 파일 업로드
    public String upload(MultipartFile file, String dirName) throws IOException, S3Exception {

        // 이미지 파일 검증
        FileUtil.validateImageFile(file);

        String originalFilename = file.getOriginalFilename();
        String ext = FileUtil.getFileExtension(file);
        String key = generateS3Key(dirName, ext);

        // PutObjectRequest 객체를 통해 파일 업로드 설정
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .contentType(file.getContentType())
                .build();

        // 파일을 S3에 업로드
        s3Client.putObject(putObjectRequest, RequestBody.fromBytes(file.getBytes()));

        // 업로드된 파일의 URL 반환
        return generateS3Url(key);
    }

    // 파일 수정 (기존 파일을 삭제하고 새 파일을 업로드)
    public String updateFile(String fileUrl, MultipartFile newFile) throws IOException, S3Exception {
        // 이미지 파일 검증
        FileUtil.validateImageFile(newFile);

        // 기존 파일 삭제
        deleteFile(fileUrl);

        // 기존 디렉토리와 파일 이름 추출
        String directoryName = extractDirectoryNameFromS3Url(fileUrl);
        String newFileName = extractFileNameFromS3Url(fileUrl);

        // 새로운 파일 업로드
        return upload(newFile, directoryName);
    }

    // 파일 삭제 (S3에서 파일 삭제)
    public void deleteFile(String s3Url) throws S3Exception {
        // S3 URL에서 파일 키를 추출
        String fileKey = extractFileKey(s3Url);

        // S3에서 파일 삭제
        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(fileKey)
                .build();
        s3Client.deleteObject(deleteObjectRequest);
    }

    private String generateS3Url(String key) {
        return "https://" + bucketName + ".s3.amazonaws.com/" + key;
    }

    private String generateS3Key(String dirName, String ext) {
        return dirName + "/" + UUID.randomUUID() + ext;
    }

    // URL에서 파일 키 추출 (파일 경로)
    private String extractFileKey(String fileUrl) {
        String prefix = "https://" + bucketName + ".s3.amazonaws.com/";
        return fileUrl.replace(prefix, "");
    }

    // URL에서 디렉토리 이름 추출
    private String extractDirectoryNameFromS3Url(String fileUrl) {
        String fileKey = extractFileKey(fileUrl);
        int lastSlashIndex = fileKey.lastIndexOf('/');
        return (lastSlashIndex != -1) ? fileKey.substring(0, lastSlashIndex) : "";
    }

    // URL에서 파일 이름 추출
    private String extractFileNameFromS3Url(String fileUrl) {
        String fileKey = extractFileKey(fileUrl);
        int lastSlashIndex = fileKey.lastIndexOf('/');
        return (lastSlashIndex != -1) ? fileKey.substring(lastSlashIndex + 1) : fileKey;
    }
}
