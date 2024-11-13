package com.scrumflow.domain.service;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.scrumflow.domain.dto.EmailDTO;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    @Value("${email.host}")
    private String host;

    @Value("${email.port}")
    private String port;

    @Value("${email.username}")
    private String username;

    @Value("${email.password}")
    private String password;

    public void sendEmail(EmailDTO emailDTO) {
        Session session = getSession();
        sendEmail(getEmailInfo(emailDTO), session);
    }

    private Session getSession() {
        return Session.getInstance(
                getProperties(),
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
    }

    private Properties getProperties() {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);

        return props;
    }

    private void sendEmail(EmailInfo emailInfo, Session session) {

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailInfo.email()));
            message.setSubject(emailInfo.assunto());
            message.setText(emailInfo.conteudo());

            Transport.send(message);

            log.info("E-mail enviado com sucesso para: " + emailInfo.email());
        } catch (MessagingException e) {
            log.warn("Erro ao enviar o e-mail: " + e.getMessage());
        }
    }

    private EmailInfo getEmailInfo(EmailDTO emailDTO) {
        return EmailInfo.builder()
                .email(emailDTO.user().getEmail())
                .assunto(String.format("ScrunmFlow: atualização de Status [%s]", emailDTO.taskName()))
                .conteudo(
                        String.format(
                                "A tarefa %s, sob sua reponsabilidade, foi atualizada. %s -> %s",
                                emailDTO.taskName(), emailDTO.oldStatus(), emailDTO.newStatus()))
                .build();
    }

    @Builder
    private record EmailInfo(String email, String assunto, String conteudo) {}
}
