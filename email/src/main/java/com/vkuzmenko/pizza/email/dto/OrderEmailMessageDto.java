package com.vkuzmenko.pizza.email.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class OrderEmailMessageDto {
    private long orderId;
    private String firstName;
    private String lastName;
    private String email;
    private String comment;
    private Date orderDate;
    private int subtotal;
    private int total;
    private int shippingPrice;
    private String shippingMethod;
    private String paymentMethod;
    private List<OrderProductEmailMessageDto> orderProducts = new ArrayList<>();
}


