package com.vkuzmenko.pizza.product.repository;

import com.vkuzmenko.pizza.product.domain.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Long> {
    List<Category> findAll();

    List<Category> findAllByOrderBySort();

    Optional<Category> findById(Long id);
}
