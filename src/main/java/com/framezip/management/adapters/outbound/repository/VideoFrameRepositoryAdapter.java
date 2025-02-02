package com.framezip.management.adapters.outbound.repository;

import com.framezip.management.application.core.domain.VideoFrame;
import com.framezip.management.application.core.mapper.VideoFrameMapper;
import com.framezip.management.application.core.repository.VideoFrameRepository;
import com.framezip.management.application.core.repository.document.VideoDocument;
import com.framezip.management.application.exception.ResourceNotFoundException;
import com.framezip.management.application.ports.out.VideoFrameRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class VideoFrameRepositoryAdapter implements VideoFrameRepositoryPort {


    private final VideoFrameMapper mapper;
    private final VideoFrameRepository repository;

    @Override
    public void saveVideoFrame(VideoFrame videoFrame) {
        log.info("Save Video Frame correlationId: {}", videoFrame.getCorrelationId());
        var videoDocument = mapper.domainToDocument(videoFrame);
        var save = repository.save(videoDocument);
        videoFrame.setId(save.getId());
    }

    @Override
    public List<VideoDocument> getVideoByUserId(String userId, String correlationId) {

        log.info("Get Video By User Id CorrelationId: {}", correlationId);
        var videoDocument = repository.findByUserIdAndCorrelationId(userId, correlationId);

        if (videoDocument.isEmpty()) {
            throw new ResourceNotFoundException("Video document not found for userId: " + userId + ", correlationId: " + correlationId);
        }
        return videoDocument;
    }

    @Override
    public Optional<VideoDocument> getVideoByUserIdAndFileId(String userId, String fileId) {

        return repository.findByUserIdAndId(userId, fileId);
    }
}
