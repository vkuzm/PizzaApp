package com.vkuzmenko.pizza.admin.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Order {
    private long orderId;
    private String firstName;
    private String lastName;
    private String email;
    private String comment;
    private String orderDate;
    private Integer subtotal;
    private Integer total;
    private String shippingMethod;
    private String paymentMethod;
    private List<OrderProduct> orderProducts = new ArrayList<>();
}
