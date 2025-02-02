package com.framezip.management.adapters.inbound.controller.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProcessResponse {

    private String correlationId;
    private List<VideoProcessResponse> videos;
}
