package com.framezip.management.application.core.mapper;

import com.framezip.management.adapters.inbound.controller.response.VideoFrameResponse;
import com.framezip.management.application.core.domain.VideoFrame;
import com.framezip.management.application.core.repository.document.VideoDocument;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class VideoFrameMapperTest {

    @InjectMocks
    private VideoFrameMapper mapper = VideoFrameMapper.INSTANCE;

    @Test
    public void testDomainToDocument() {
        // Crie um objeto VideoFrame de exemplo
        VideoFrame videoFrame = new VideoFrame();
        videoFrame.setId("value"); // Popule os campos conforme necessário

        // Converta para VideoDocument
        VideoDocument videoDocument = mapper.domainToDocument(videoFrame);

        // Assegure-se de que os campos foram mapeados corretamente
        assertEquals(videoFrame.getId(), videoDocument.getId());
    }

    @Test
    public void testDocumentToResponse() {
        // Crie um objeto VideoDocument de exemplo
        VideoDocument videoDocument = new VideoDocument();
        videoDocument.setId("value"); // Popule os campos conforme necessário

        // Converta para VideoFrameResponse
        VideoFrameResponse videoFrameResponse = mapper.documentToResponse(videoDocument);

        // Assegure-se de que os campos foram mapeados corretamente
        assertEquals(videoDocument.getId(), videoFrameResponse.getId());
    }

    @Test
    public void testDocumentToResponseList() {
        // Crie uma lista de objetos VideoDocument de exemplo
        VideoDocument videoDocument1 = new VideoDocument();
        videoDocument1.setId("value1");
        VideoDocument videoDocument2 = new VideoDocument();
        videoDocument2.setId("value2");
        List<VideoDocument> videoDocuments = Arrays.asList(videoDocument1, videoDocument2);

        // Converta para lista de VideoFrameResponse
        List<VideoFrameResponse> videoFrameResponses = mapper.documentToResponse(videoDocuments);

        // Assegure-se de que os campos foram mapeados corretamente
        assertEquals(videoDocuments.get(0).getId(), videoFrameResponses.getFirst().getId());
        assertEquals(videoDocuments.get(1).getId(), videoFrameResponses.get(1).getId());
    }
}