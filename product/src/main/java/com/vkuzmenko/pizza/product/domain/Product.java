package com.vkuzmenko.pizza.product.domain;

import lombok.Data;
import org.springframework.format.annotation.NumberFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Data
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotEmpty
    private String name;

    @Column(length = 10000)
    private String description;

    @Column(length = 500)
    private String shortDescription;

    @NumberFormat
    private Integer price;
    private String image;
    private Boolean hot;
    private String createdAt;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id")
    private Category category;

    @Transient
    private long categoryId;
}
