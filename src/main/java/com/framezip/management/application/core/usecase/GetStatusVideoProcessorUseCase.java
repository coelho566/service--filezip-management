package com.framezip.management.application.core.usecase;

import com.framezip.management.adapters.inbound.controller.response.VideoFrameResponse;

import java.util.List;

public interface GetStatusVideoProcessorUseCase {

    List<VideoFrameResponse> getStatusVideoFrame(String userId, String correlationId);
}
