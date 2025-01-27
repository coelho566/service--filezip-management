package com.framezip.management.application.core.usecase.impl;

import com.framezip.management.adapters.inbound.controller.VideoFrameResponse;
import com.framezip.management.application.core.mapper.VideoFrameMapper;
import com.framezip.management.application.core.usecase.GetStatusVideoProcessorUseCase;
import com.framezip.management.application.ports.out.VideoFrameRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetStatusVideoProcessorUseCaseImpl implements GetStatusVideoProcessorUseCase {

    private final VideoFrameMapper mapper;
    private final VideoFrameRepositoryPort videoFrameRepositoryPort;

    @Override
    public List<VideoFrameResponse> getStatusVideoFrame(String userId, String correlationId) {
        var videoByUserId = videoFrameRepositoryPort.getVideoByUserId(userId, correlationId);
        return mapper.documentToResponse(videoByUserId);
    }
}
