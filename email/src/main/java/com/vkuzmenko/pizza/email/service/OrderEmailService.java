package com.vkuzmenko.pizza.email.service;

import com.vkuzmenko.pizza.email.dto.OrderEmailMessageDto;

public interface OrderEmailService {
    boolean sendOrderEmail(OrderEmailMessageDto email, String subject);
}
