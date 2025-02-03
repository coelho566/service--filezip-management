package com.framezip.management.adapters.inbound.controller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VideoProcessResponse {

    private String videoId;
    private String presignedUrl;
    private String fileName;
    private Double duration;
}
