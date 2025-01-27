package com.framezip.management.application.core.mapper;

import com.framezip.management.adapters.inbound.controller.VideoFrameResponse;
import com.framezip.management.application.core.domain.VideoFrame;
import com.framezip.management.application.core.repository.document.VideoDocument;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface VideoFrameMapper {

    VideoFrameMapper INSTANCE = Mappers.getMapper(VideoFrameMapper.class);

    VideoDocument domainToDocument(VideoFrame videoFrame);

    VideoFrameResponse documentToResponse(VideoDocument videoFrame);

    List<VideoFrameResponse> documentToResponse(List<VideoDocument> videoFrame);
}
