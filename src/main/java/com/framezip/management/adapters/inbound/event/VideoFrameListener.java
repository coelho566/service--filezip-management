package com.framezip.management.adapters.inbound.event;

import com.framezip.management.application.core.usecase.SendEmailUseCase;
import io.awspring.cloud.sqs.annotation.SqsListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class VideoFrameListener {

    private final SendEmailUseCase sendEmailUseCase;

    @SqsListener("${aws.sqs.queue-name}")
    public void handleS3Event(String message) throws IOException {

        log.info("Received S3 event: {}", message);
        sendEmailUseCase.sendEmail(message);
    }
}
