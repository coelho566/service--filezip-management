package com.framezip.management.adapters.inbound.storage;

import com.framezip.management.application.exception.BusinessException;
import com.framezip.management.application.ports.in.DownloadZipStoragePort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class DownloadZipStorageAdapter implements DownloadZipStoragePort {

    @Value("${s3.bucket.name}")
    private String bucketName;
    private final S3Client s3Client;

    @Override
    public byte[] downloadZipBucket(String fileKey) {

        try {
            log.info("download zip file bucket {}", fileKey);
            var getObjectRequest = GetObjectRequest.builder()
                    .bucket(bucketName)
                    .key("zip/" + fileKey + ".zip")
                    .build();

            var s3Object = s3Client.getObject(getObjectRequest);

            var outputStream = new ByteArrayOutputStream();
            s3Object.transferTo(outputStream);
            return outputStream.toByteArray();

        } catch (S3Exception e) {
            log.error("download zip file bucket {} error", bucketName, e);
            throw new BusinessException("download zip file bucket " + bucketName + " error");
        } catch (IOException e) {
            log.error("download zip file bucket {} error", bucketName, e);
            throw new BusinessException("Error transforming zip file bucket " + bucketName + " error");
        }
    }
}
