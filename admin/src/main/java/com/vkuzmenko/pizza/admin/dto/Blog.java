package com.vkuzmenko.pizza.admin.dto;

import lombok.Data;

@Data
public class Blog {
    private long postId;
    private String title;
    private String description;
    private String shortDescription;
    private String image;
    private String createdAt;
}
