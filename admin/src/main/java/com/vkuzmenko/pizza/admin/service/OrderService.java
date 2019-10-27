package com.vkuzmenko.pizza.admin.service;

import com.vkuzmenko.pizza.admin.dto.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface OrderService {
    PagedResources<Resource<Order>> findAll(Pageable pageable);

    Optional<Order> findById(Long id);

    ResponseEntity<String> update(Order order);

    ResponseEntity<String> insert(Order order);

    ResponseEntity<String> delete(Long id);
}
