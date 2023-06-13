package com.Projekat_Web.Projekat_Web.controller;

import com.Projekat_Web.Projekat_Web.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailRestController {


    @Autowired
    private EmailService emailSenderService;


    @GetMapping("/api/send-email")
    public ResponseEntity sendEmailReject(@RequestParam("email") String emailMessage) {
        String subject = "Aktivacija korisničkog računa";
        String message = "Pozdrav! Vaš korisnički račun je neuspešno aktiviran.";
        this.emailSenderService.sendEmail(emailMessage, subject, message);
        return ResponseEntity.ok("Success");
    }
    @GetMapping("/api/send-email2")
    public ResponseEntity sendEmailApproved(@RequestParam("email") String emailMessage, String lozinka) {
        String subject = "Aktivacija korisničkog računa";
        String message = "Pozdrav! Vaš korisnički račun je uspesno aktiviran. Ovo je vaša lozinka: " + lozinka;
        this.emailSenderService.sendEmail(emailMessage, subject, message);
        return ResponseEntity.ok("Success");
    }
}
