package com.framezip.management.application.core.usecase;

import com.framezip.management.adapters.inbound.controller.request.VideoFrameRequest;
import com.framezip.management.adapters.inbound.controller.response.ProcessResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CreateFileZipUseCase {

    ProcessResponse uploadVideo(VideoFrameRequest videoFrameRequest, List<MultipartFile> files);
}
