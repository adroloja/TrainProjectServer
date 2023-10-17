package com.adrianj.trainproject.domain.controller;

import com.adrianj.trainproject.domain.services.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;

@RestController
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;

    @GetMapping("/sendEmail")
    public void sendEmail() throws MessagingException {

        emailService.sendTicketConfirmationEmail("adroloja@gmail.com", "Prueba", "Estos es una prueba");
    }
}
