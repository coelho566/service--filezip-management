package com.framezip.management.application.core.usecase.impl;

import com.framezip.management.adapters.inbound.controller.request.VideoFrameRequest;
import com.framezip.management.adapters.inbound.controller.response.ProcessResponse;
import com.framezip.management.adapters.inbound.controller.response.VideoProcessResponse;
import com.framezip.management.application.core.domain.ProcessorStatus;
import com.framezip.management.application.core.domain.User;
import com.framezip.management.application.core.domain.VideoFrame;
import com.framezip.management.application.core.usecase.CreateFileZipUseCase;
import com.framezip.management.application.ports.out.UploadVideoStoragePort;
import com.framezip.management.application.ports.out.VideoFrameRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreateFileZipUseCaseImpl implements CreateFileZipUseCase {

    private final VideoFrameRepositoryPort videoFrameRepositoryPort;
    private final UploadVideoStoragePort uploadVideoStoragePort;

    @Override
    public ProcessResponse uploadVideo(VideoFrameRequest videoFrameRequest, User user) {

        var correlationId = UUID.randomUUID().toString();
        var processResponse = new ProcessResponse();
        var videos = new ArrayList<VideoProcessResponse>();
        processResponse.setCorrelationId(correlationId);
        videoFrameRequest.getZipInfo().forEach(file -> {

            log.info("Generete presigned url video file {}", file.getFileName());
            var videoFrame = builVideoFrameDomain(user, file.getFileName(), correlationId, file.getIntervalFrame());
            videoFrameRepositoryPort.saveVideoFrame(videoFrame);
            var presignedUrl = uploadVideoStoragePort.uploadVideoBucket(videoFrame.getId());
            var videoProcessResponse = VideoProcessResponse.builder()
                    .videoId(videoFrame.getId())
                    .presignedUrl(presignedUrl)
                    .fileName(file.getFileName())
                    .duration(file.getIntervalFrame())
                    .build();
            videos.add(videoProcessResponse);
        });

        processResponse.setVideos(videos);

        return processResponse;
    }

    private VideoFrame builVideoFrameDomain(User user, String originalFileName, String correlationId, Double intervalFrame) {
        var videoFrame = new VideoFrame();
        videoFrame.setName(originalFileName);
        videoFrame.setUserId(user.getId());
        videoFrame.setUserName(user.getName());
        videoFrame.setUserEmail(user.getEmail());
        videoFrame.setProcessorStatus(ProcessorStatus.RECEIVED);
        videoFrame.setIntervalFrame(intervalFrame);
        videoFrame.setCorrelationId(correlationId);
        return videoFrame;
    }
}
