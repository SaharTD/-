package com.example.final_project.Notification;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationService {

    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String senderEmail;


    public void sendEmail(String sendToEmail, String subject, String message){

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(senderEmail);
        simpleMailMessage.setTo(sendToEmail);
        simpleMailMessage.setText(message);
        simpleMailMessage.setSubject(subject);
//        simpleMailMessage.setSentDate(Date.from(Instant.now()));
        javaMailSender.send(simpleMailMessage);

    }
}
