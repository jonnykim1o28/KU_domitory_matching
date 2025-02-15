package com.suvey.suvey.global.mail;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender emailSender;

    public void sendEmail(String toEmail, String title, String text) {
        SimpleMailMessage emailForm = createEmailForm(toEmail, title, text);
        try {
            System.out.println(emailForm);
            emailSender.send(emailForm);
        } catch (RuntimeException e) {
            log.debug("MailService.sendEmail exception occur toEmail: {}, " +
                    "title: {}, text: {}", toEmail, title, text);
            System.out.println("MailService.sendEmail exception occur toEmail: " + toEmail + ", title: " + title + ", text: " + text);
            System.out.println(e.getMessage());
        } catch (Exception e) {
            e.getMessage();
        }
        System.out.println("email has been sent");
    }

    private SimpleMailMessage createEmailForm(String toEmail, String title, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("linkstorage4703@gmail.com");
        message.setTo(toEmail);
        message.setSubject(title);
        message.setText(text);

        return message;
    }
}
