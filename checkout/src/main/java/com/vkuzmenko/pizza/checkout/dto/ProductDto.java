package com.vkuzmenko.pizza.checkout.dto;

import lombok.Data;

@Data
public final class ProductDto {
    private Long productId;
    private String name;
    private String description;
    private String shortDescription;
    private int price;
    private String image;
    private Boolean hot;
    private String createdAt;
}
