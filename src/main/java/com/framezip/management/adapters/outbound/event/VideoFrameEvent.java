package com.framezip.management.adapters.outbound.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.framezip.management.application.core.domain.VideoFrame;
import com.framezip.management.application.ports.out.VideoFrameEventPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class VideoFrameEvent implements VideoFrameEventPort {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @Override
    public void sendVideoFrameProcessor(VideoFrame videoFrame) {
        log.info("Send video processor {}", videoFrame.getName());
        try {
            kafkaTemplate.send("send-framezip-processor-topic", objectMapper.writeValueAsString(videoFrame));
        } catch (JsonProcessingException e) {
            log.error("Error send message topic {}",e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }
}
