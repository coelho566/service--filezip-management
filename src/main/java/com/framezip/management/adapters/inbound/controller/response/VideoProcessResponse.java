package com.framezip.management.adapters.inbound.controller.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VideoProcessResponse {

    private String zipId;
    private String fileName;
    private Double duration;
}
