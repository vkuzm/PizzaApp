package com.vkuzmenko.pizza.product.service;

import com.vkuzmenko.pizza.product.domain.Category;
import javassist.NotFoundException;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    List<Category> findAll();

    Optional<Category> findById(Long id);

    List<Category> getShowCase(Integer productLimit);

    Optional<Category> save(Category category);

    Optional<Category> update(Long id, Category category) throws NotFoundException;

    void delete(Long id) throws NotFoundException;
}
