package com.vkuzmenko.pizza.email.service;

import com.vkuzmenko.pizza.email.dto.OrderEmailMessageDto;
import com.vkuzmenko.pizza.email.dto.OrderProductEmailMessageDto;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import static com.vkuzmenko.pizza.email.Constants.ADMIN_EMAIL;

@Service
public class OrderEmailServiceImpl implements OrderEmailService{
    private final JavaMailSender emailSender;

    public OrderEmailServiceImpl(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    @Override
    public boolean sendOrderEmail(OrderEmailMessageDto email, String subject) {
        String emailText = orderEmailMessage(email);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email.getEmail(), ADMIN_EMAIL);
        message.setFrom(ADMIN_EMAIL);
        message.setSubject(subject);
        message.setText(emailText);

        try {
            emailSender.send(message);
        } catch (MailException e) {
            e.printStackTrace();
        }

        return true;
    }

    private String orderEmailMessage(OrderEmailMessageDto email) {
        StringBuilder text = new StringBuilder("Order ID: " + email.getOrderId() +
                "\nFirst name: " + email.getFirstName() +
                "\nLast name: " + email.getLastName() +
                "\nEmail: " + email.getEmail() +
                "\nComment: " + email.getComment() +
                "\nOrder date: " + email.getOrderDate() +
                "\nSubtotal: " + email.getSubtotal() +
                "\nTotal: " + email.getTotal() +
                "\nShipping method: " + email.getShippingMethod() +
                "\nPayment method: " + email.getPaymentMethod());

        text.append("\n\nProducts:\n");
        for (OrderProductEmailMessageDto product : email.getOrderProducts()) {
            text.append("Product: ").append(product.getProductName());
            text.append("\nQuantity: x").append(product.getQuantity());
            text.append("\nPrice: ").append(product.getPrice());
            text.append("\nTotal: ").append(product.getTotal());
            text.append("\n\n");
        }

        return text.toString();
    }
}
