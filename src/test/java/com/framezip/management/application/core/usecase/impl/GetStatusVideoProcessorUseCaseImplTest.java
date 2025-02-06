package com.framezip.management.application.core.usecase.impl;

import com.framezip.management.adapters.inbound.controller.response.VideoFrameResponse;
import com.framezip.management.application.core.mapper.VideoFrameMapper;
import com.framezip.management.application.core.repository.document.VideoDocument;
import com.framezip.management.application.ports.out.VideoFrameRepositoryPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GetStatusVideoProcessorUseCaseImplTest {

    @Mock
    private VideoFrameMapper mapper;

    @Mock
    private VideoFrameRepositoryPort videoFrameRepositoryPort;

    @InjectMocks
    private GetStatusVideoProcessorUseCaseImpl getStatusVideoProcessorUseCaseImpl;


    @Test
    public void testGetStatusVideoFrame() {
        // Arrange
        String userId = "user1";
        String correlationId = "corr1";
        var videoFrames = List.of(new VideoDocument(), new VideoDocument());
        var videoFrameResponses = List.of(new VideoFrameResponse(), new VideoFrameResponse());

        when(videoFrameRepositoryPort.getVideoByUserId(userId, correlationId)).thenReturn(videoFrames);
        when(mapper.documentToResponse(videoFrames)).thenReturn(videoFrameResponses);

        // Act
        List<VideoFrameResponse> result = getStatusVideoProcessorUseCaseImpl.getStatusVideoFrame(userId, correlationId);

        // Assert
        assertNotNull(result);
        assertEquals(videoFrameResponses.size(), result.size());
        verify(videoFrameRepositoryPort, times(1)).getVideoByUserId(userId, correlationId);
        verify(mapper, times(1)).documentToResponse(videoFrames);
    }

    @Test
    public void testGetStatusVideoFrame_EmptyResponse() {
        // Arrange
        String userId = "user2";
        String correlationId = "corr2";
        List<VideoDocument> videoFrames = List.of();
        List<VideoFrameResponse> videoFrameResponses = List.of();

        when(videoFrameRepositoryPort.getVideoByUserId(userId, correlationId)).thenReturn(videoFrames);
        when(mapper.documentToResponse(videoFrames)).thenReturn(videoFrameResponses);

        // Act
        List<VideoFrameResponse> result = getStatusVideoProcessorUseCaseImpl.getStatusVideoFrame(userId, correlationId);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(videoFrameRepositoryPort, times(1)).getVideoByUserId(userId, correlationId);
        verify(mapper, times(1)).documentToResponse(videoFrames);
    }
}
