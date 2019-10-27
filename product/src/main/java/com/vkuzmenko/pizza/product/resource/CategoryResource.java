package com.vkuzmenko.pizza.product.resource;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vkuzmenko.pizza.product.domain.Category;
import com.vkuzmenko.pizza.product.domain.Product;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

import java.util.List;

@Relation(value = "category", collectionRelation = "categories")
public class CategoryResource extends ResourceSupport {

    @Getter
    private final Long categoryId;

    @Getter
    private final String name;

    @Getter
    private final String description;

    @Getter
    private final String createdAt;

    @Getter
    @JsonIgnore
    private final List<Product> productsList;

    @Getter
    @Setter
    private List<ProductResource> products;

    public CategoryResource(Category category) {
        this.categoryId = category.getCategoryId();
        this.name = category.getName();
        this.description = category.getDescription();
        this.createdAt = category.getCreatedAt();
        this.productsList = category.getProducts();
    }
}