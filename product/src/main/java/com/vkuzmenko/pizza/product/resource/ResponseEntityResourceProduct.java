package com.vkuzmenko.pizza.product.resource;

import com.vkuzmenko.pizza.product.domain.Product;
import org.springframework.hateoas.Resources;

import java.util.List;

public class ResponseEntityResourceProduct {

    private ResponseEntityResourceProduct() {
    }

    public static Resources<ProductResource> convert(List<Product> products) {
        List<ProductResource> productResource
                = new ProductResourceAssembler().toResources(products);

        return new Resources<>(productResource);
    }

    public static ProductResource convert(Product product) {
        return new ProductResourceAssembler().toResource(product);
    }
}
