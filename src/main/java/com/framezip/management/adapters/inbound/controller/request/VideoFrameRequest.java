package com.framezip.management.adapters.inbound.controller.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VideoFrameRequest {

  private List<ZipRequest> zipInfo;
}
