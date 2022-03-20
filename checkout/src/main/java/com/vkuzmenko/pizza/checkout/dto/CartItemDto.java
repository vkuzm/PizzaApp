package com.vkuzmenko.pizza.checkout.dto;

import lombok.Data;

@Data
public class CartItemDto {
    private long productId;
    private String name;
    private String shortDescription;
    private int price;
    private int total;
    private String image;
    private int quantity;
}
