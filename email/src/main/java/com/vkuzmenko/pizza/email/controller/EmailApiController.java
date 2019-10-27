package com.vkuzmenko.pizza.email.controller;

import com.vkuzmenko.pizza.email.dto.EmailMessageDto;
import com.vkuzmenko.pizza.email.service.ContactEmailService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.vkuzmenko.pizza.email.Constants.*;

@RestController
@RequestMapping(value = API_URL + "/" + API_URL_EMAIL, produces = "application/json")
public class EmailApiController {
    private final ContactEmailService emailService;

    public EmailApiController(ContactEmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/contactForm")
    public ResponseEntity<?> sendContact(
            @Valid @RequestBody EmailMessageDto email,
            BindingResult errors) {

        if (errors.hasErrors()) {
            return new ResponseEntity<>(errors.getAllErrors(), HttpStatus.BAD_REQUEST);
        }

        try {
            emailService.sendContactForm(email, ADMIN_EMAIL, EMAIL_CONTACT_SUBJECT);
        } catch (MailException e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok().build();
    }

}
