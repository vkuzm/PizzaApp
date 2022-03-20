package com.vkuzmenko.pizza.email.service;

import com.vkuzmenko.pizza.email.dto.EmailMessageDto;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class ContactEmailServiceImpl implements ContactEmailService {
    private final JavaMailSender emailSender;

    public ContactEmailServiceImpl(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    @Override
    public void sendContactForm(EmailMessageDto email, String to, String subject) throws MailException {
        String emailText = contactEmailMessage(email);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setFrom(email.getEmail());
        message.setText(emailText);

        if (email.getSubject() != null && email.getSubject().length() > 0) {
            message.setSubject(email.getSubject());
        } else {
            message.setSubject(subject);
        }

        emailSender.send(message);
    }

    private String contactEmailMessage(EmailMessageDto email) {
        return "\nName: " + email.getName() +
                "\nEmail: " + email.getEmail() +
                "Message: " + email.getMessage();
    }

}
