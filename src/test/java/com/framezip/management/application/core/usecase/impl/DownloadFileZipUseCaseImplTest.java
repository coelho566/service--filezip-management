package com.framezip.management.application.core.usecase.impl;

import com.framezip.management.application.core.repository.document.VideoDocument;
import com.framezip.management.application.exception.ResourceNotFoundException;
import com.framezip.management.application.ports.in.DownloadZipStoragePort;
import com.framezip.management.application.ports.out.VideoFrameRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.client.ExpectedCount.never;

@ExtendWith(MockitoExtension.class)
public class DownloadFileZipUseCaseImplTest {

    @Mock
    private DownloadZipStoragePort downloadZipStoragePort;

    @Mock
    private VideoFrameRepositoryPort videoFrameRepositoryPort;

    @InjectMocks
    private DownloadFileZipUseCaseImpl downloadFileZipUseCase;

    private String userId;
    private String fileName;

    @BeforeEach
    public void setup() {
        userId = "testUserId";
        fileName = "testFileName";
    }

    @Test
    public void testDownloadFileZip_Success() {
        var video = new VideoDocument(); // Suponha que Video é a classe apropriada
        video.setId("testVideoId");

        when(videoFrameRepositoryPort.getVideoByUserIdAndFileId(userId, fileName)).thenReturn(Optional.of(video));
        when(downloadZipStoragePort.downloadZipBucket(video.getId())).thenReturn(new byte[0]);

        byte[] result = downloadFileZipUseCase.downloadFileZip(userId, fileName);

        assertNotNull(result);
        verify(videoFrameRepositoryPort).getVideoByUserIdAndFileId(userId, fileName);
        verify(downloadZipStoragePort).downloadZipBucket(video.getId());
    }

    @Test
    public void testDownloadFileZip_FileNotFound() {
        when(videoFrameRepositoryPort.getVideoByUserIdAndFileId(userId, fileName)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            downloadFileZipUseCase.downloadFileZip(userId, fileName);
        });

        String expectedMessage = "File not found for userId: " + userId;
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        verify(videoFrameRepositoryPort).getVideoByUserIdAndFileId(userId, fileName);
        verify(downloadZipStoragePort, times(0)).downloadZipBucket(anyString());
    }
}