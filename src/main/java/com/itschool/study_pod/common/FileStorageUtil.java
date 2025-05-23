package com.itschool.study_pod.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Slf4j
@Component
public class FileStorageUtil {

    private final String UPLOAD_DIR = "uploads"; // 상대 경로로 저장될 디렉토리

    public static String saveFile(MultipartFile file, String uploadDirPath, String fileName) {
        try {
            File uploadDir = new File(uploadDirPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs(); // 디렉토리가 없으면 생성
            }

            File destFile = new File(uploadDir, fileName);
            file.transferTo(destFile);

            return destFile.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("파일 저장 중 오류 발생", e);
        }
    }
}

