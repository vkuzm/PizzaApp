package com.vkuzmenko.pizza.checkout.service;

import com.vkuzmenko.pizza.checkout.domain.Order;

public interface NotificationService {
    void orderEmailNotification(Order order);
}
