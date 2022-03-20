package com.vkuzmenko.pizza.email.service;

import com.vkuzmenko.pizza.email.dto.EmailMessageDto;
import org.springframework.mail.MailException;

public interface ContactEmailService {
    void sendContactForm(EmailMessageDto email, String to, String subject) throws MailException;
}
