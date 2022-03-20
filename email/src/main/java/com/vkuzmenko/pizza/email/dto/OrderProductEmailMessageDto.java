package com.vkuzmenko.pizza.email.dto;

import lombok.Data;

@Data
public class OrderProductEmailMessageDto {
    private String productName;
    private int quantity;
    private int price;
    private int total;
}

