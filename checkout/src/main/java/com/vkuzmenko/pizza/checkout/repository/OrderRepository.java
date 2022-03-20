package com.vkuzmenko.pizza.checkout.repository;

import com.vkuzmenko.pizza.checkout.domain.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {
    Page<Order> findAll(Pageable pageable);

    Optional<Order> findById(Long orderId);
}
