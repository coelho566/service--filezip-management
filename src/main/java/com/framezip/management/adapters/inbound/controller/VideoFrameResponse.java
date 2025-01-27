package com.framezip.management.adapters.inbound.controller;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class VideoFrameResponse {

    private String id;
    private String name;
    private String userId;
    private String userName;
    private String userEmail;
    private String directory;
    private String correlationId;
    private Double intervalFrame;
    private String processorStatus;
}
