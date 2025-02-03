package com.framezip.management.adapters.inbound.controller.request;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class VideoFrameRequest {

  private List<ZipRequest> zipInfo;
}
