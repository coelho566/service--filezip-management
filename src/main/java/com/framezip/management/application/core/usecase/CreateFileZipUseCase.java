package com.framezip.management.application.core.usecase;

import com.framezip.management.adapters.inbound.controller.VideoFrameRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CreateFileZipUseCase {

    void uploadVideo(VideoFrameRequest videoFrameRequest, List<MultipartFile> files);
}
