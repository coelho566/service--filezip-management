package com.framezip.management.adapters.inbound.controller.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VideoFrameRequest {

    private String name;
    private String userId;
    private String userEmail;
    private String userName;
    private String directory;
    private String correlationId;
    private Double intervalFrame;
}
