package com.framezip.management.adapters.inbound.event;

import com.framezip.management.application.core.usecase.SendEmailUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class VideoFrameListenerTest {

    @InjectMocks
    private VideoFrameListener videoFrameListener;

    @Mock
    private SendEmailUseCase sendEmailUseCase;

    @Test
    void handleS3Event() {
        var fileId = UUID.randomUUID().toString();

        videoFrameListener.handleS3Event(fileId);
        verify(sendEmailUseCase, times(1)).sendEmail(fileId);
    }
}