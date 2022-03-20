package com.vkuzmenko.pizza.information.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "pages")
public class Page {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String title;

    @Column(length = 10000)
    private String description;
    private String h1;

    @Column(length = 1000)
    private String shortDescription;
    private Date createdAt;
    private String href;

    @PrePersist
    void createdAt() {
        this.createdAt = new Date();
    }
}
