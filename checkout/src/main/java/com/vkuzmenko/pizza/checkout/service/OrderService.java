package com.vkuzmenko.pizza.checkout.service;

import com.vkuzmenko.pizza.checkout.domain.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface OrderService {
    Page<Order> findAll(Pageable pageable);

    Optional<Order> findById(Long orderId);

    Optional<Order> save(Order order);

    Optional<Order> update(Long id, Order order);

    void delete(Long id);
}
