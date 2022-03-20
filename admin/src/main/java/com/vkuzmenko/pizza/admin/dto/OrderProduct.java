package com.vkuzmenko.pizza.admin.dto;

import lombok.Data;

@Data
public class OrderProduct {
    private long id;
    private String productName;
    private int quantity;
    private int price;
    private int total;
    private Order order;
}
