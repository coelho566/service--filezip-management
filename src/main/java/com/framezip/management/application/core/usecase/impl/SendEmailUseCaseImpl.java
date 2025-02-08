package com.framezip.management.application.core.usecase.impl;

import com.framezip.management.adapters.outbound.repository.VideoFrameRepositoryAdapter;
import com.framezip.management.application.config.MailerSendProperties;
import com.framezip.management.application.core.usecase.SendEmailUseCase;
import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.emails.Email;
import com.mailersend.sdk.exceptions.MailerSendException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SendEmailUseCaseImpl implements SendEmailUseCase {

    private final MailerSendProperties mailerSendProperties;
    private final VideoFrameRepositoryAdapter videoFrameRepository;

    @Override
    public void sendEmail(String fileId) {
        log.info("Sending email to {}", fileId);
        var videoById = videoFrameRepository.getVideoById(fileId);
        videoById.ifPresent(video -> {

            var email = getEmail(video.getUserName(), video.getName(), video.getUserEmail());
            var mailerSend = new MailerSend();
            mailerSend.setToken(mailerSendProperties.getToken());

            try {
                var response = mailerSend.emails().send(email);
                log.info("Email sending with success {}", response.messageId);
            } catch (MailerSendException e) {
                log.error("Error sending email", e);
            }
        });
    }

    private Email getEmail(String userName, String fileName, String userEmail) {
        var email = new Email();

        email.setFrom("File Zipe Company", mailerSendProperties.getEmail());
        email.addRecipient("name", userEmail);
        email.setSubject("File Zipe Company");
        email.setPlain("Vídeo Não Processado {{ fileName }}.");
        email.setHtml(getTemplate());

        email.addPersonalization("userName", userName);
        email.addPersonalization("fileName", fileName);
        return email;
    }

    private String getTemplate() {
        return "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <title>Vídeo Não Processado</title>\n" +
                "    <style>\n" +
                "        body {\n" +
                "            font-family: Arial, sans-serif;\n" +
                "            background-color: #f4f4f4;\n" +
                "            margin: 0;\n" +
                "            padding: 0;\n" +
                "        }\n" +
                "        .container {\n" +
                "            max-width: 600px;\n" +
                "            margin: 20px auto;\n" +
                "            background: #ffffff;\n" +
                "            padding: 20px;\n" +
                "            border-radius: 8px;\n" +
                "            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);\n" +
                "            text-align: center;\n" +
                "        }\n" +
                "        .header {\n" +
                "            font-size: 24px;\n" +
                "            font-weight: bold;\n" +
                "            color: #333;\n" +
                "        }\n" +
                "        .message {\n" +
                "            font-size: 16px;\n" +
                "            color: #555;\n" +
                "            margin-top: 10px;\n" +
                "        }\n" +
                "        .footer {\n" +
                "            margin-top: 20px;\n" +
                "            font-size: 14px;\n" +
                "            color: #777;\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <div class=\"container\">\n" +
                "        <div class=\"header\">Aviso: Vídeo Não Processado</div>\n" +
                "        <p class=\"message\"> {{ userName }} infelizmente, seu vídeo {{ fileName }} não pôde ser processado no momento. \n" +
                "        Por favor, tente novamente mais tarde ou entre em contato com o suporte para mais informações.</p>\n" +
                "        <p class=\"footer\">Atenciosamente,<br>Equipe de Suporte</p>\n" +
                "    </div>\n" +
                "</body>\n" +
                "</html>\n";
    }
}
