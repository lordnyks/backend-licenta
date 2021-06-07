package com.monitoring.documents.controllers;

import com.monitoring.documents.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Email;

@RestController
@CrossOrigin(origins = "*")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/sendEmail")
    public String sendEmailMessage(/*@RequestParam(required = false) String email, @RequestParam(required = false) String subject, @RequestParam(required = false String message*/) {
        emailService.sendMessage( //
                "nicusorin98@yahoo.com", //
                "test", //
                "endava power" //
        );

        return "Email-ul a fost trimis cu succes!";
    }

}
