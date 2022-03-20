package com.vkuzmenko.pizza.product.resource;

import com.vkuzmenko.pizza.product.domain.Product;
import lombok.Getter;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

@Relation(value = "product", collectionRelation = "products")
public class ProductResource extends ResourceSupport {

    @Getter
    private final long productId;

    @Getter
    private final String name;

    @Getter
    private final String description;

    @Getter
    private final String shortDescription;

    @Getter
    private final Integer price;

    @Getter
    private final String image;

    @Getter
    private final Boolean hot;

    @Getter
    private final String createdAt;

    @Getter
    private final CategoryResource category;

    public ProductResource(Product product) {
        this.productId = product.getId();
        this.name = product.getName();
        this.description = product.getDescription();
        this.shortDescription = product.getShortDescription();
        this.price = product.getPrice();
        this.image = product.getImage();
        this.hot = product.getHot();
        this.createdAt = product.getCreatedAt();
        this.category = product.getCategory() != null ?
                new CategoryResourceAssembler().toResource(product.getCategory()) : null;
    }
}