package com.vkuzmenko.pizza.admin.dto;

import lombok.Data;

@Data
public class Category {
    private long categoryId;
    private String name;
    private String description;
    private String createdAt;
}
