package com.adrianj.trainproject.domain.controller;

import com.adrianj.trainproject.domain.services.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;
    @GetMapping("/email")
    public ResponseEntity<String> sendEmail(){

        emailService.sendMail();

        return ResponseEntity.ok("ok");
    }
}
