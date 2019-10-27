package com.vkuzmenko.pizza.admin.dto;

import lombok.Data;

@Data
public class Product {
    private long productId;
    private String name;
    private String shortDescription;
    private Double price;
    private String image;
    private Boolean hot;
    private String createdAt;
    private Category category;
    private long categoryId;
}
