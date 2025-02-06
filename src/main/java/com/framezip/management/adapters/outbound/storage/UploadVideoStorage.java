package com.framezip.management.adapters.outbound.storage;


import com.framezip.management.application.exception.BusinessException;
import com.framezip.management.application.ports.out.UploadVideoStoragePort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

import java.time.Duration;

@Slf4j
@Component
@RequiredArgsConstructor
public class UploadVideoStorage implements UploadVideoStoragePort {

    @Value("${s3.bucket.name}")
    private String bucketName;
    private final S3Presigner presigner;

    @Override
    public String uploadVideoBucket(String fileName) {

        try {
            log.info("Generete presign url video bucket : {}", fileName);
            var request = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(String.format("videos/%s.mp4", fileName))
                    .contentType("video/mp4")
                    .build();

            var presignRequest = PutObjectPresignRequest.builder()
                    .signatureDuration(Duration.ofMinutes(5))
                    .putObjectRequest(request)
                    .build();

            var presignedPutObjectRequest = presigner.presignPutObject(presignRequest);
            return presignedPutObjectRequest.url().toString();


        } catch (S3Exception e) {
            log.error("Error occurred while genereted presigner url video bucket", e);
            throw new BusinessException("Error occurred while genereted presigner url video bucket");
        }
    }
}


