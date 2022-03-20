package com.vkuzmenko.pizza.product.resource;

import com.vkuzmenko.pizza.product.domain.Category;
import org.springframework.hateoas.Resources;

import java.util.List;

public class ResponseEntityResourceCategory {

    private ResponseEntityResourceCategory() {
    }

    public static Resources<CategoryResource> convert(List<Category> categories, boolean includeProducts) {
        List<CategoryResource> categoryResourceList
                = new CategoryResourceAssembler().toResources(categories);

        Resources<CategoryResource> categoryResources = new Resources<>(categoryResourceList);

        if (includeProducts) {
            convertProducts(categoryResources);
        }

        return categoryResources;
    }

    private static void convertProducts(Resources<CategoryResource> categoryResources) {
        for (CategoryResource category : categoryResources) {

            if (!category.getProductsList().isEmpty()) {
                List<ProductResource> productResourceList
                        = new ProductResourceAssembler().toResources(category.getProductsList());

                if (!productResourceList.isEmpty()) {
                    category.setProducts(productResourceList);
                }
            }
        }
    }

    public static CategoryResource convert(Category category) {
        CategoryResource categoryResource
                = new CategoryResourceAssembler().toResource(category);

        if (!categoryResource.getProductsList().isEmpty()) {
            List<ProductResource> productResourceList = new ProductResourceAssembler()
                    .toResources(categoryResource.getProductsList());

            if (!productResourceList.isEmpty()) {
                categoryResource.setProducts(productResourceList);
            }
        }

        return categoryResource;
    }
}
