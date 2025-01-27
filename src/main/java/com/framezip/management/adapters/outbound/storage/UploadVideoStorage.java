package com.framezip.management.adapters.outbound.storage;


import com.framezip.management.application.ports.out.UploadVideoStoragePort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class UploadVideoStorage implements UploadVideoStoragePort {

    private final S3Client s3Client;
    private final String bucketName = "service-management-bucket";

    @Override
    public void uploadVideoBucket(String fileName, MultipartFile file) {

        try {
            log.info("Uploading video bucket : {}", fileName);
            var contentType = file.getContentType();
            if (contentType == null || !contentType.startsWith("video/")) {
                log.info("Content-Type not supported: {}", file.getContentType());
//            return ResponseEntity.badRequest().body("O arquivo enviado não é um vídeo!");
                throw new RuntimeException();
            }

            var resourcePath = Paths.get("src/main/resources/temp");
            if (!Files.exists(resourcePath)) {
                Files.createDirectories(resourcePath);
            }

            Path tempFile = Files.createTempFile("video-", "-" + fileName);
            file.transferTo(tempFile.toFile());

            var request = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(String.format("videos/%s", fileName))
                    .contentType(contentType)
                    .build();

            PutObjectResponse putObjectResponse = s3Client.putObject(request, RequestBody.fromFile(tempFile));
            // Remove o arquivo temporário
            Files.deleteIfExists(tempFile);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }
}


