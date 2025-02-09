package com.framezip.management.adapters.inbound.event;

import com.framezip.management.application.core.usecase.SendEmailUseCase;
import io.awspring.cloud.sqs.annotation.SqsListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class VideoFrameListener {

    private final SendEmailUseCase sendEmailUseCase;

    @SqsListener("https://sqs.us-east-1.amazonaws.com/110832778598/filezip-send_email_queue")
    public void handleS3Event(String message) {

        log.info("Received S3 event: {}", message);
        sendEmailUseCase.sendEmail(message);
    }
}
