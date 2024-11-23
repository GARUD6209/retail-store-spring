package com.garud.retail.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.Properties;
import jakarta.mail.*;
import jakarta.mail.internet.*;

@Component
@Slf4j
public class MailUtil {

    @Value("${spring.mail.host}")
    private String smtpHost;

    @Value("${spring.mail.port}")
    private String smtpPort;

    @Value("${spring.mail.username}")
    private String smtpAuthUser;

    @Value("${spring.mail.password}")
    private String smtpAuthPassword;


    public boolean sendEmail(String toEmail, String subject, String messageContent) throws MessagingException {
        try {
            Properties props = new Properties();
            props.put("mail.smtp.host", smtpHost);
            props.put("mail.smtp.port", smtpPort);
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");

            Session session = Session.getInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(smtpAuthUser, smtpAuthPassword);
                }
            });

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(smtpAuthUser));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject(subject);
            message.setText(messageContent);

            Transport.send(message);

            // Log success or return true
            log.info("Email sent successfully to: " + toEmail);
            return true;

        } catch (MessagingException e) {
            // Log failure or return false
            log.error("Failed to send email to: " + toEmail);
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}