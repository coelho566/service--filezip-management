package com.framezip.management.application.core.usecase.impl;

import com.framezip.management.adapters.outbound.repository.VideoFrameRepositoryAdapter;
import com.framezip.management.application.config.MailerSendProperties;
import com.framezip.management.application.core.repository.document.VideoDocument;
import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.emails.Emails;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SendEmailUseCaseImplTest {

    @InjectMocks
    private SendEmailUseCaseImpl target;
    @Mock
    private MailerSend mailerSend;
    @Mock
    private MailerSendProperties mailerSendProperties;
    @Mock
    private VideoFrameRepositoryAdapter videoFrameRepository;

    @Test
    void sendEmail() {
        var videoFrame = new VideoDocument();
        videoFrame.setUserName("test");
        videoFrame.setName("videoFrame.mp4");
        videoFrame.setUserEmail("test@user.com");

        when(videoFrameRepository.getVideoById(any())).thenReturn(Optional.of(videoFrame));
        when(mailerSendProperties.getEmail()).thenReturn("test@user.com");
        when(mailerSendProperties.getToken()).thenReturn("test");

        target.sendEmail("1");

        verify(videoFrameRepository, times(1)).getVideoById(any());
    }
}