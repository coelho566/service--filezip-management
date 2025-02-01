package com.framezip.management.adapters.outbound.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.framezip.management.adapters.inbound.controller.response.ProcessResponse;
import com.framezip.management.adapters.inbound.controller.response.VideoProcessResponse;
import com.framezip.management.application.core.domain.VideoFrame;
import com.framezip.management.application.exception.BusinessException;
import com.framezip.management.application.ports.out.VideoFrameEventPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class VideoFrameEvent implements VideoFrameEventPort {

    public static final String SEND_FRAMEZIP_PROCESSOR_TOPIC = "send-framezip-processor-topic";
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @Override
    public void sendVideoFrameProcessor(ProcessResponse processResponse) {
        log.info("Send video processor {}", processResponse.getCorrelationId());
        try {
            kafkaTemplate.send(SEND_FRAMEZIP_PROCESSOR_TOPIC, objectMapper.writeValueAsString(processResponse));
        } catch (JsonProcessingException e) {
            log.error("Error send message topic {}", e.getMessage(), e);
            throw new BusinessException("Error send message topic " + SEND_FRAMEZIP_PROCESSOR_TOPIC);
        }
    }
}
