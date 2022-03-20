package com.vkuzmenko.pizza.admin.dto;

import lombok.Data;

@Data
public class Slider {
    private long id;
    private long productId;
    private String title;
    private String subtitle;
    private String description;
    private String image;
    private boolean orderButton;
    private boolean viewButton;
    private boolean backgroundImage;
    private boolean justifyCenter;
    private String styles;
}
