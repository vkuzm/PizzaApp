package com.vkuzmenko.pizza.admin.dto;

import lombok.Data;

@Data
public class CustomField {
    private long id;
    private String fieldName;
    private String content;
    private int sort;
}
