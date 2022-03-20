package com.vkuzmenko.pizza.email.controller;

import com.vkuzmenko.pizza.email.dto.OrderEmailMessageDto;
import com.vkuzmenko.pizza.email.service.OrderEmailService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.vkuzmenko.pizza.email.Constants.API_URL;
import static com.vkuzmenko.pizza.email.Constants.API_URL_ORDER_EMAIL;

@RestController
@RequestMapping(value = API_URL + "/" + API_URL_ORDER_EMAIL, produces = "application/json")
public class OrderEmailController {
    private final OrderEmailService orderEmailService;

    public OrderEmailController(OrderEmailService orderEmailService) {
        this.orderEmailService = orderEmailService;
    }

    @PostMapping
    public ResponseEntity sendContact(@RequestBody OrderEmailMessageDto email) {
        String subject = "Order # " + email.getOrderId() + " is created.";
        if (orderEmailService.sendOrderEmail(email, subject)) {
            return new ResponseEntity<>(email, HttpStatus.OK);
        }

        return ResponseEntity.badRequest().build();
    }

}