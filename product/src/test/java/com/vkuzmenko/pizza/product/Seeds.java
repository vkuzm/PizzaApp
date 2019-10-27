package com.vkuzmenko.pizza.product;

import com.vkuzmenko.pizza.product.domain.Category;
import com.vkuzmenko.pizza.product.domain.Product;

import java.util.ArrayList;
import java.util.List;

public class Seeds {

    public static Category generateCategory(long categoryId, String categoryName, int productLimit) {
        Category category = new Category();
        if (categoryId > 0) {
            category.setCategoryId(categoryId);
        }
        category.setName(categoryName);
        category.setCreatedAt("12/12/2012");
        category.setDescription("Category description " + categoryId);

        List<Product> productList = new ArrayList<>();

        for (int i = 1; i <= productLimit; i++) {
            Product product = new Product();
            if (categoryId > 0) {
                product.setId(i);
                product.setCategoryId(categoryId);
            }
            product.setName("Product name " + i);
            product.setDescription("Product description " + i);
            product.setImage("product_" + i + ".jpg");
            product.setHot(true);
            product.setPrice(100);
            product.setCreatedAt("12/12/2012");
            product.setShortDescription("Short description " + i);

            productList.add(product);
        }

        category.setProducts(productList);

        return category;
    }

    public static Product generateProduct(long productId, String productName) {
        Product product = new Product();
        product.setId(productId);
        product.setName(productName);
        product.setDescription("Product description " + productId);
        product.setImage("product_" + productId + ".jpg");
        product.setHot(true);
        product.setPrice(100);
        product.setCreatedAt("12/12/2012");
        product.setShortDescription("Short description " + productId);
        product.setCategoryId(1L);

        Category category = new Category();

        if (productId > 0) {
            category.setCategoryId(1L);
        }
        category.setName("Category name " + productId);
        category.setCreatedAt("12/12/2012");
        category.setDescription("Category description " + productId);
        product.setCategory(category);

        return product;
    }
}
