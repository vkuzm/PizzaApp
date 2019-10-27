package com.vkuzmenko.pizza.admin.service;

import com.vkuzmenko.pizza.admin.dto.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface ProductService {
    PagedResources<Resource<Product>> findAll(Pageable pageable);

    Optional<Product> findById(Long id);

    ResponseEntity<String> insert(Product product, MultipartFile imageFile);

    ResponseEntity<String> update(Product product, MultipartFile imageFile);

    ResponseEntity<String> delete(Long id);
}
