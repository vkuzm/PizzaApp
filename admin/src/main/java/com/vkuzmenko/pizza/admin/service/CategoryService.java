package com.vkuzmenko.pizza.admin.service;

import com.vkuzmenko.pizza.admin.dto.Category;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    List<Category> findAll();

    Optional<Category> findById(Long id);

    ResponseEntity<String> insert(Category category);

    ResponseEntity<String> update(Category category);

    ResponseEntity<String> delete(Long id);
}
