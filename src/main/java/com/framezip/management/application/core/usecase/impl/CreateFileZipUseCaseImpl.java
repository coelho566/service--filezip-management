package com.framezip.management.application.core.usecase.impl;

import com.framezip.management.adapters.inbound.controller.request.VideoFrameRequest;
import com.framezip.management.adapters.inbound.controller.response.ProcessResponse;
import com.framezip.management.adapters.inbound.controller.response.VideoProcessResponse;
import com.framezip.management.application.core.domain.ProcessorStatus;
import com.framezip.management.application.core.domain.VideoFrame;
import com.framezip.management.application.core.usecase.CreateFileZipUseCase;
import com.framezip.management.application.ports.out.UploadVideoStoragePort;
import com.framezip.management.application.ports.out.VideoFrameEventPort;
import com.framezip.management.application.ports.out.VideoFrameRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreateFileZipUseCaseImpl implements CreateFileZipUseCase {

    private final VideoFrameRepositoryPort videoFrameRepositoryPort;
    private final UploadVideoStoragePort uploadVideoStoragePort;
    private final VideoFrameEventPort videoFrameEventPort;

    @Override
    public ProcessResponse uploadVideo(VideoFrameRequest videoFrameRequest, List<MultipartFile> files) {

        var correlationId = UUID.randomUUID().toString();
        var processResponse = new ProcessResponse();
        var videos = new ArrayList<VideoProcessResponse>();
        processResponse.setCorrelationId(correlationId);
        files.forEach(file -> {

            log.info("Uploading video file {}", file.getOriginalFilename());
            var videoFrame = builVideoFrameDomain(videoFrameRequest, file.getOriginalFilename());
            videoFrameRepositoryPort.saveVideoFrame(videoFrame);
            uploadVideoStoragePort.uploadVideoBucket(videoFrame.getDirectory(), file);
            videoFrameEventPort.sendVideoFrameProcessor(videoFrame);
            var videoProcessResponse = new VideoProcessResponse(videoFrame.getId(), videoFrame.getName());
            videos.add(videoProcessResponse);
        });
        processResponse.setVideos(videos);

        return processResponse;
    }

    private VideoFrame builVideoFrameDomain(VideoFrameRequest videoFrameRequest, String originalFileName) {
        //Todo - recuperar o formato do video
        var fileName = String.format("%s.mp4", UUID.randomUUID());
        var videoFrame = new VideoFrame();
        videoFrame.setDirectory(fileName);
        videoFrame.setName(originalFileName);
        videoFrame.setUserId(videoFrameRequest.getUserId());
        videoFrame.setUserName(videoFrameRequest.getUserName());
        videoFrame.setUserEmail(videoFrameRequest.getUserEmail());
        videoFrame.setProcessorStatus(ProcessorStatus.RECEIVED);
        videoFrame.setIntervalFrame(videoFrameRequest.getIntervalFrame());
        videoFrame.setCorrelationId(videoFrameRequest.getCorrelationId());
        return videoFrame;
    }
}
