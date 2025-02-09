package com.framezip.management.adapters.outbound.repository;

import com.framezip.management.application.core.domain.VideoFrame;
import com.framezip.management.application.core.mapper.VideoFrameMapper;
import com.framezip.management.application.core.repository.VideoFrameRepository;
import com.framezip.management.application.core.repository.document.VideoDocument;
import com.framezip.management.application.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VideoFrameRepositoryAdapterTest {

    @Mock
    private VideoFrameMapper mapper;

    @Mock
    private VideoFrameRepository repository;

    @InjectMocks
    private VideoFrameRepositoryAdapter adapter;


    private VideoFrame videoFrame;
    private VideoDocument videoDocument;

    @BeforeEach
    void setUp() {
        videoFrame = new VideoFrame();
        videoFrame.setCorrelationId("testCorrelationId");

        videoDocument = new VideoDocument();
        videoDocument.setId("testId");
    }

    @Test
    void testSaveVideoFrame() {
        when(mapper.domainToDocument(videoFrame)).thenReturn(videoDocument);
        when(repository.save(videoDocument)).thenReturn(videoDocument);

        adapter.saveVideoFrame(videoFrame);

        verify(repository, times(1)).save(videoDocument);
        assertEquals("testId", videoFrame.getId());
    }

    @Test
    void testGetVideoByUserId() {
        when(repository.findByUserIdAndCorrelationId("userId", "correlationId"))
                .thenReturn(List.of(videoDocument));

        List<VideoDocument> result = adapter.getVideoByUserId("userId", "correlationId");

        assertEquals(1, result.size());
        assertEquals("testId", result.getFirst().getId());
    }

    @Test
    void testGetVideoByUserIdAndFileId() {
        when(repository.findByUserIdAndId("userId", "fileId"))
                .thenReturn(Optional.of(videoDocument));

        Optional<VideoDocument> result = adapter.getVideoByUserIdAndFileId("userId", "fileId");

        assertTrue(result.isPresent());
        assertEquals("testId", result.get().getId());
    }

    @Test
    void testGetVideoByUserIdNotFound() {
        when(repository.findByUserIdAndCorrelationId("userId", "correlationId"))
                .thenReturn(List.of());

        Exception exception = assertThrows(ResourceNotFoundException.class, () ->
                adapter.getVideoByUserId("userId", "correlationId"));
        assertEquals("Video document not found for userId: userId, correlationId: correlationId", exception.getMessage());
    }
}

