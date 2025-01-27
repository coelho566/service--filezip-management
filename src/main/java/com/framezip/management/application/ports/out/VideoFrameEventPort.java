package com.framezip.management.application.ports.out;

import com.framezip.management.application.core.domain.VideoFrame;

public interface VideoFrameEventPort {

    void sendVideoFrameProcessor(VideoFrame videoFrame);
}
