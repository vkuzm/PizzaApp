package com.vkuzmenko.pizza.product.resource;

import com.vkuzmenko.pizza.product.controller.CategoryApiController;
import com.vkuzmenko.pizza.product.domain.Category;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

public class CategoryResourceAssembler extends ResourceAssemblerSupport<Category, CategoryResource> {

    public CategoryResourceAssembler() {
        super(CategoryApiController.class, CategoryResource.class);
    }

    @Override
    protected CategoryResource instantiateResource(Category category) {
        return new CategoryResource(category);
    }

    @Override
    public CategoryResource toResource(Category category) {
        return createResourceWithId(category.getCategoryId(), category);
    }
}
