package com.vkuzmenko.pizza.checkout.service;

import com.vkuzmenko.pizza.checkout.domain.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class NotificationServiceImpl implements NotificationService {
    private final RestTemplate restTemplate;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public NotificationServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public void orderEmailNotification(Order order) {
        new Thread(() -> {
            String orderEmailUrl = "http://gateway-service:8080/email-service/api/orderEmail";
            ResponseEntity<String> result =
                    restTemplate.postForEntity(orderEmailUrl, order, String.class);

            if (result.getStatusCode() == HttpStatus.OK) {
                logger.info("Email for order # " + order.getOrderId() + " has sent");
            } else {
                logger.warn("Email for order # " + order.getOrderId() + " has not sent");
            }
        }).start();
    }
}
