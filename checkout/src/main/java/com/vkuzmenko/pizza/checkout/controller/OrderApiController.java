package com.vkuzmenko.pizza.checkout.controller;

import com.vkuzmenko.pizza.checkout.domain.Order;
import com.vkuzmenko.pizza.checkout.resource.OrderResource;
import com.vkuzmenko.pizza.checkout.resource.OrderResourceAssembler;
import com.vkuzmenko.pizza.checkout.service.OrderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/orders", produces = "application/json")
@SuppressWarnings("Duplicates") // The same code in modules that supposed to be different micro-services.
public class OrderApiController {
    private final OrderService orderService;
    private final OrderResourceAssembler orderResAssembler;

    public OrderApiController(OrderService orderService, OrderResourceAssembler orderResAssembler) {
        this.orderService = orderService;
        this.orderResAssembler = orderResAssembler;
    }

    @GetMapping(produces = "application/hal+json")
    public ResponseEntity<PagedResources<OrderResource>> findAll(
            Pageable pageable,
            PagedResourcesAssembler<Order> assembler) {

        Page<Order> orders = orderService.findAll(pageable);
        if (!orders.isEmpty()) {
            return new ResponseEntity<>(assembler
                    .toResource(orders, orderResAssembler), HttpStatus.OK);
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> findById(@PathVariable("id") Long orderId) {
        Optional<Order> order = orderService.findById(orderId);

        return order.map(o ->
                new ResponseEntity<>(o, HttpStatus.OK))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> create(
            @Valid @RequestBody Order order,
            BindingResult errors) {

        if (!errors.hasErrors()) {
            Optional<Order> savedOrder = orderService.save(order);

            if (savedOrder.isPresent()) {
                return new ResponseEntity<>(savedOrder.get(), HttpStatus.CREATED);
            }
        }

        return new ResponseEntity<>(errors.getAllErrors(), HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> edit(
            @PathVariable("id") Long id,
            @Valid @RequestBody Order order,
            BindingResult errors) {

        if (!errors.hasErrors()) {
            Optional<Order> updatedOrder = orderService.update(id, order);

            if (updatedOrder.isPresent()) {
                return new ResponseEntity<>(updatedOrder, HttpStatus.OK);
            }
        }

        return new ResponseEntity<>(errors.getAllErrors(), HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        orderService.delete(id);
    }
}
