package com.framezip.management.application.core.usecase;

import com.framezip.management.adapters.inbound.controller.request.VideoFrameRequest;
import com.framezip.management.adapters.inbound.controller.response.ProcessResponse;
import com.framezip.management.application.core.domain.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CreateFileZipUseCase {

    ProcessResponse uploadVideo(VideoFrameRequest videoFrameRequest, User user);
}
