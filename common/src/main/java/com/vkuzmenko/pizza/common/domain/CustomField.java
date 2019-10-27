package com.vkuzmenko.pizza.common.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Data
@Entity
@Table(name = "custom_fields")
public class CustomField {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotEmpty
    private String fieldName;

    @Column(length = 10000)
    @NotEmpty
    private String content;

    @Min(0)
    private int sort;
}
