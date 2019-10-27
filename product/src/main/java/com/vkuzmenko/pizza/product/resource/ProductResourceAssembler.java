package com.vkuzmenko.pizza.product.resource;

import com.vkuzmenko.pizza.product.controller.ProductApiController;
import com.vkuzmenko.pizza.product.domain.Product;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class ProductResourceAssembler extends ResourceAssemblerSupport<Product, ProductResource> {

    public ProductResourceAssembler() {
        super(ProductApiController.class, ProductResource.class);
    }

    @Override
    protected ProductResource instantiateResource(Product product) {
        return new ProductResource(product);
    }

    @Override
    public ProductResource toResource(Product product) {
        return createResourceWithId(product.getId(), product);
    }
}
