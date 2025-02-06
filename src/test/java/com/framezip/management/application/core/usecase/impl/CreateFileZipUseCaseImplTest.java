package com.framezip.management.application.core.usecase.impl;

import com.framezip.management.adapters.inbound.controller.request.VideoFrameRequest;
import com.framezip.management.adapters.inbound.controller.request.ZipRequest;
import com.framezip.management.application.core.domain.User;
import com.framezip.management.application.ports.out.UploadVideoStoragePort;
import com.framezip.management.application.ports.out.VideoFrameRepositoryPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateFileZipUseCaseImplTest {

    @Mock
    private VideoFrameRepositoryPort videoFrameRepositoryPort;

    @Mock
    private UploadVideoStoragePort uploadVideoStoragePort;

    @InjectMocks
    private CreateFileZipUseCaseImpl createFileZipUseCase;


    @Test
    void testUploadVideo() {
        var videoFrameRequest = new VideoFrameRequest();
        var user = new User();
        user.setId("user123");
        user.setName("testuser");
        user.setEmail("testuser@example.com");

        var zipInfo = new ZipRequest();
        zipInfo.setFileName("file1.mp4");
        zipInfo.setIntervalFrame(10.0);
        videoFrameRequest.setZipInfo(List.of(zipInfo));

        when(uploadVideoStoragePort.uploadVideoBucket(Mockito.any())).thenReturn("presignedUrl");

        var response = createFileZipUseCase.uploadVideo(videoFrameRequest, user);

        assertNotNull(response);
        assertEquals(1, response.getVideos().size());
        var videoResponse = response.getVideos().getFirst();
        assertEquals("file1.mp4", videoResponse.getFileName());
        assertEquals("presignedUrl", videoResponse.getPresignedUrl());
        assertEquals(10.0, videoResponse.getDuration());
        verify(uploadVideoStoragePort, times(1)).uploadVideoBucket(Mockito.any());
    }

}