package com.framezip.management.application.ports.out;

import com.framezip.management.adapters.inbound.controller.response.ProcessResponse;
import com.framezip.management.adapters.inbound.controller.response.VideoProcessResponse;
import com.framezip.management.application.core.domain.VideoFrame;

public interface VideoFrameEventPort {

    void sendVideoFrameProcessor(ProcessResponse processResponse);
}
