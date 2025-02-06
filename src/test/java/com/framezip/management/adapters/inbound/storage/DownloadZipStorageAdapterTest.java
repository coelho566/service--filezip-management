package com.framezip.management.adapters.inbound.storage;

import com.framezip.management.application.exception.BusinessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.util.ReflectionTestUtils;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.http.AbortableInputStream;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.services.s3.model.S3Object;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestPropertySource(properties = "s3.bucket.name=test-bucket")
public class DownloadZipStorageAdapterTest {

    @Mock
    private S3Client s3Client;

    @InjectMocks
    private DownloadZipStorageAdapter downloadZipStorageAdapter;

    @BeforeEach
    void setup() {
        downloadZipStorageAdapter = new DownloadZipStorageAdapter(s3Client);
        ReflectionTestUtils.setField(downloadZipStorageAdapter, "bucketName", "test-bucket");
    }

    @Test
    public void testDownloadZipBucketSuccess() throws IOException {
        var fileKey = "test-file";
        byte[] expectedBytes = "dummy content".getBytes();


        var mockInputStream = new ByteArrayInputStream(expectedBytes);
        var mockOutputStream = new ByteArrayOutputStream();
        mockInputStream.transferTo(mockOutputStream);
        var getObjectResponseResponseInputStream = new ResponseInputStream<>(
                GetObjectResponse.builder().build(), AbortableInputStream.create(mockInputStream));

        when(s3Client.getObject(Mockito.any(GetObjectRequest.class))).thenReturn(getObjectResponseResponseInputStream);

        byte[] result = downloadZipStorageAdapter.downloadZipBucket(fileKey);

        assertNotNull(result);
        verify(s3Client, times(1)).getObject(any(GetObjectRequest.class));
    }

    @Test
    public void testDownloadZipBucketS3Exception() {
        String fileKey = "test-file";

        when(s3Client.getObject(any(GetObjectRequest.class))).thenThrow(S3Exception.class);

        BusinessException exception = assertThrows(BusinessException.class, () -> {
            downloadZipStorageAdapter.downloadZipBucket(fileKey);
        });

        assertEquals("download zip file bucket test-bucket error", exception.getMessage());
        verify(s3Client, times(1)).getObject(any(GetObjectRequest.class));
    }

    @Test
    public void testDownloadZipBucketIOException() throws IOException {
        String fileKey = "test-file";

        var getObjectResponseResponseInputStream = mock(ResponseInputStream.class);

        when(s3Client.getObject(Mockito.any(GetObjectRequest.class))).thenReturn(getObjectResponseResponseInputStream);
        when(getObjectResponseResponseInputStream.transferTo(any(ByteArrayOutputStream.class))).thenThrow(IOException.class);

        BusinessException exception = assertThrows(BusinessException.class, () -> {
            downloadZipStorageAdapter.downloadZipBucket(fileKey);
        });

        assertEquals("Error transforming zip file bucket test-bucket error", exception.getMessage());
        verify(s3Client, times(1)).getObject(any(GetObjectRequest.class));
    }
}