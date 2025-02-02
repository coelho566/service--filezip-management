package com.framezip.management.adapters.outbound.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.framezip.management.adapters.inbound.controller.response.ProcessResponse;
import com.framezip.management.application.exception.BusinessException;
import com.framezip.management.application.ports.out.VideoFrameEventPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

@Slf4j
@Component
@RequiredArgsConstructor
public class VideoFrameEvent implements VideoFrameEventPort {

    public static final String SEND_FRAMEZIP_PROCESSOR_TOPIC = "send-framezip-processor-topic";
    private final SqsClient sqsClient;
    private final ObjectMapper objectMapper;

    @Override
    public void sendVideoFrameProcessor(ProcessResponse processResponse) {
        log.info("Send video processor {}", processResponse.getCorrelationId());
        try {
            var message = objectMapper.writeValueAsString(processResponse);
            var sendMsgRequest = SendMessageRequest.builder()
                    .queueUrl("https://sqs.us-east-1.amazonaws.com/110832778598/filezip-event-create-queue")
                    .messageBody(message)
                    .build();

            var response = sqsClient.sendMessage(sendMsgRequest);
            log.info("Send video frame processor response: {}", response.messageId());
        } catch (JsonProcessingException e) {
            log.error("Error send message topic {}", e.getMessage(), e);
            throw new BusinessException("Error send message topic " + SEND_FRAMEZIP_PROCESSOR_TOPIC);
        }
    }
}
