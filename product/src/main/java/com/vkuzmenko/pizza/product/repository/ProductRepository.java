package com.vkuzmenko.pizza.product.repository;

import com.vkuzmenko.pizza.product.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {

    Page<Product> findAll(Pageable pageable);

    Optional<Product> findById(Long id);

    List<Product> findAllByHotIsTrue();

    @Query(value = "SELECT p.* FROM products p " +
            "WHERE p.category_id = ?1 ORDER BY p.created_at DESC LIMIT ?2",
            nativeQuery = true)
    List<Product> findByCategoryId(Long categoryId, Integer limit);
}
