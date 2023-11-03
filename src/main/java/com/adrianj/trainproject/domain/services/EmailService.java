package com.adrianj.trainproject.domain.services;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender sender;

    public void sendMail(){

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setFrom("ilikejamon@gmail.com");
        mail.setTo("adroloja@gmail.com");
        mail.setText("esto es una prueba");
        mail.setSubject("Testing");

        sender.send(mail);
        System.out.println("Email is sent");
    }
}
