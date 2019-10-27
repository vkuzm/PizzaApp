package com.vkuzmenko.pizza.common.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Data
@Entity
@Table(name = "slider")
public class Slider {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(columnDefinition = "int default 0")
    private long productId;

    @NotEmpty
    private String title;
    private String subtitle;

    @Column(length = 1000)
    @NotEmpty
    private String description;
    private String image;
    private boolean orderButton;
    private boolean viewButton;
    private boolean backgroundImage;
    private boolean justifyCenter;
    private String styles;
}
