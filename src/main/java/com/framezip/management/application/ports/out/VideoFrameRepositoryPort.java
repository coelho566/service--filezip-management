package com.framezip.management.application.ports.out;

import com.framezip.management.application.core.domain.VideoFrame;
import com.framezip.management.application.core.repository.document.VideoDocument;

import java.util.List;
import java.util.Optional;

public interface VideoFrameRepositoryPort {

    void saveVideoFrame(VideoFrame videoFrame);

    List<VideoDocument> getVideoByUserId(String userId, String correlationId);
    Optional<VideoDocument> getVideoByUserIdAndFileId(String userId, String fileId);
}
