package com.adrianj.trainproject.domain.services;

import com.adrianj.trainproject.domain.entities.Passenger;
import com.adrianj.trainproject.domain.entities.Ticket;
import com.adrianj.trainproject.domain.exception.ResourceNotFoundException;
import com.adrianj.trainproject.domain.repositories.PassengerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Log4j2
public class EmailService {

    private final JavaMailSender sender;
    private final PassengerRepository passengerRepository;

    @Value("${base.url}")
    private String baseUrl;
    public void sendCodeValidation(long passengerId, String email, String token){

        MimeMessage message = sender.createMimeMessage();

        String url = baseUrl + "confirmValidate/" + passengerId + "/" + token;

        try{

            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom("ilikeeljamon@gmail.com");
            helper.setTo(email);
            helper.setSubject("Email validation");

            String htmlContent = "<div style=\"flex-direction: column; justify-content: center; align-items: center;\">" +
                    "  <div style=\"width: 100%\"><h1>Email validation</h1></div><br>" +
                    " <div style=\"width: 100%\"><p>To validate the email just click on the link:</p></div>" +
                    " <div style=\"width: 100%\"><a href=\"" + url + "\">Click here</a></div>" +
                    "</div>";


            helper.setText(htmlContent, true);
            sender.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }

    public void sendTicket(Ticket ticket){


    }
}
