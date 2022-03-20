package com.vkuzmenko.pizza.checkout.service;

import com.vkuzmenko.pizza.checkout.domain.Order;
import com.vkuzmenko.pizza.checkout.domain.OrderProduct;
import com.vkuzmenko.pizza.checkout.repository.OrderRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepo;
    private final NotificationService notificationService;

    public OrderServiceImpl(OrderRepository orderRepo, NotificationService notificationService) {
        this.orderRepo = orderRepo;
        this.notificationService = notificationService;
    }

    @Override
    public Page<Order> findAll(Pageable pageable) {
        return orderRepo.findAll(pageable);
    }

    @Override
    public Optional<Order> findById(Long orderId) {
        return orderRepo.findById(orderId);
    }

    @Override
    public Optional<Order> save(Order order) {
        for (OrderProduct product : order.getOrderProducts()) {
            product.setOrder(order);
        }

        Order savedOrder = orderRepo.save(order);
        if (savedOrder.getOrderId() > 0) {
            notificationService.orderEmailNotification(savedOrder);
        }
        return Optional.of(savedOrder);
    }

    @Override
    public Optional<Order> update(Long id, Order order) {
        Optional<Order> persistedOrder = orderRepo.findById(id);

        if (!persistedOrder.isPresent()) {
            throw new RuntimeException("Persisted order ID " + id + " is not existed.");
        }

        persistedOrder.ifPresent(p -> {
            if (order.getEmail() != null) {
                p.setEmail(order.getEmail());
            }
            if (order.getFirstName() != null) {
                p.setFirstName(order.getFirstName());
            }
            if (order.getLastName() != null) {
                p.setLastName(order.getLastName());
            }
            if (order.getComment() != null) {
                p.setComment(order.getComment());
            }
            if (order.getEmail() != null) {
                p.setOrderDate(order.getOrderDate());
            }
            if (order.getPaymentMethod() != null) {
                p.setPaymentMethod(order.getPaymentMethod());
            }
            if (order.getEmail() != null) {
                p.setShippingMethod(order.getShippingMethod());
            }
            if (order.getSubtotal() != null) {
                p.setSubtotal(order.getSubtotal());
            }
            if (order.getTotal() != null) {
                p.setTotal(order.getTotal());
            }
            if (order.getOrderProducts() != null) {
                order.getOrderProducts()
                        .forEach(p::addsOrderProduct);
            }
        });

        Order savedOrder = orderRepo.save(persistedOrder.get());
        return Optional.of(savedOrder);
    }

    @Override
    public void delete(Long id) {
        orderRepo.deleteById(id);
    }
}
