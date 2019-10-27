package com.vkuzmenko.pizza.product.service;

import com.vkuzmenko.pizza.product.domain.Product;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    Page<Product> findAll(Pageable pageable);

    Optional<Product> findById(Long id);

    Optional<Product> save(Product product);

    Optional<Product> update(Long id, Product product) throws NotFoundException;

    void delete(Long id) throws NotFoundException;

    List<Product> findAllByHot();
}
