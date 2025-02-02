package com.framezip.management.adapters.outbound.storage;


import com.framezip.management.application.exception.BusinessException;
import com.framezip.management.application.ports.out.UploadVideoStoragePort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Slf4j
@Component
@RequiredArgsConstructor
public class UploadVideoStorage implements UploadVideoStoragePort {

    @Value("${s3.bucket.name}")
    private String bucketName;
    private final S3Client s3Client;

    @Override
    public void uploadVideoBucket(String fileName, MultipartFile file) {

        try {
            log.info("Uploading video bucket : {}", fileName);
            var contentType = file.getContentType();
            if (contentType == null || !contentType.startsWith("video/")) {
                log.info("Content-Type not supported: {}", file.getContentType());
                throw new BusinessException("Content-Type not supported: " + file.getContentType());
            }

            var resourcePath = Paths.get("src/main/resources/temp");
            if (!Files.exists(resourcePath)) {
                Files.createDirectories(resourcePath);
            }

            var tempFile = Files.createTempFile("video-", "-" + fileName);
            file.transferTo(tempFile.toFile());

            var request = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(String.format("videos/%s.mp4", fileName))
                    .contentType(contentType)
                    .build();

            s3Client.putObject(request, RequestBody.fromFile(tempFile));
            // Remove o arquivo temporário
            Files.deleteIfExists(tempFile);
        } catch (S3Exception e) {
            log.error("Error occurred while uploading video bucket", e);
            throw new BusinessException("Error occurred while uploading video bucket");
        } catch (IOException e) {
            log.error("Error occurred while created video file local", e);
            throw new BusinessException("Error occurred while created video file local");
        }
    }
}


