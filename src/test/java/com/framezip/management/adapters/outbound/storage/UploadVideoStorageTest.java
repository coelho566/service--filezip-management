package com.framezip.management.adapters.outbound.storage;

import com.framezip.management.adapters.inbound.storage.DownloadZipStorageAdapter;
import com.framezip.management.application.exception.BusinessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.util.ReflectionTestUtils;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;


import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UploadVideoStorageTest {

    @Mock
    private S3Presigner presigner;

    @InjectMocks
    private UploadVideoStorage uploadVideoStorage;

    @Value("${s3.bucket.name}")
    private String bucketName = "test-bucket";

    @BeforeEach
    public void setUp() {
        uploadVideoStorage = new UploadVideoStorage(presigner);
        ReflectionTestUtils.setField(uploadVideoStorage, "bucketName", "test-bucket");
    }

    @Test
    public void testUploadVideoBucket_Success() throws MalformedURLException {
        String fileName = "test-video";

        var presignedPutObjectRequest = mock(PresignedPutObjectRequest.class);
        when(presigner.presignPutObject(any(PutObjectPresignRequest.class))).thenReturn(presignedPutObjectRequest);
        when(presignedPutObjectRequest.url()).thenReturn(new URL("http://example.com"));

        String url = uploadVideoStorage.uploadVideoBucket(fileName);

        assertNotNull(url);
        assertEquals("http://example.com", url);
        verify(presigner, times(1)).presignPutObject(any(PutObjectPresignRequest.class));
    }

    @Test
    public void testUploadVideoBucket_Exception() {
        String fileName = "test-video";

        when(presigner.presignPutObject(any(PutObjectPresignRequest.class))).thenThrow(S3Exception.class);

        assertThrows(BusinessException.class, () -> {
            uploadVideoStorage.uploadVideoBucket(fileName);
        });

        verify(presigner, times(1)).presignPutObject(any(PutObjectPresignRequest.class));
    }
}
