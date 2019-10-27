package com.vkuzmenko.pizza.checkout.repository;

import com.vkuzmenko.pizza.checkout.Application;
import com.vkuzmenko.pizza.checkout.H2JpaConfig;
import com.vkuzmenko.pizza.checkout.domain.Order;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static com.vkuzmenko.pizza.checkout.Seeds.generateOrder;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class, H2JpaConfig.class})
@Transactional
public class OrderRepositoryTest {

    @Autowired
    OrderRepository orderRepository;

    @Test
    public void whenFindAll_thenReturnOrderList() {
        Order order1 = generateOrder(0, "Nick", "Black", 2);
        Order order2 = generateOrder(0, "Victor", "Strange", 5);

        orderRepository.save(order1);
        orderRepository.save(order2);

        Page<Order> orderList = orderRepository.findAll(PageRequest.of(0, 10));

        assertEquals(orderList.getContent().size(), 2);
        assertEquals(orderList.getContent().get(0).getEmail(), order1.getEmail());
        assertEquals(orderList.getContent().get(0).getFirstName(), order1.getFirstName());
        assertEquals(orderList.getContent().get(0).getLastName(), order1.getLastName());
        assertEquals(orderList.getContent().get(0).getComment(), order1.getComment());
        assertEquals(orderList.getContent().get(0).getOrderDate(), order1.getOrderDate());
        assertEquals(orderList.getContent().get(0).getPaymentMethod(), order1.getPaymentMethod());
        assertEquals(orderList.getContent().get(0).getShippingMethod(), order1.getShippingMethod());
        assertEquals(orderList.getContent().get(0).getSubtotal(), order1.getSubtotal());
        assertEquals(orderList.getContent().get(0).getTotal(), order1.getTotal());
        assertEquals(orderList.getContent().get(0).getOrderProducts().size(), order1.getOrderProducts().size());

        assertEquals(orderList.getContent().get(1).getEmail(), order2.getEmail());
        assertEquals(orderList.getContent().get(1).getFirstName(), order2.getFirstName());
        assertEquals(orderList.getContent().get(1).getLastName(), order2.getLastName());
        assertEquals(orderList.getContent().get(1).getComment(), order2.getComment());
        assertEquals(orderList.getContent().get(1).getOrderDate(), order2.getOrderDate());
        assertEquals(orderList.getContent().get(1).getPaymentMethod(), order2.getPaymentMethod());
        assertEquals(orderList.getContent().get(1).getShippingMethod(), order2.getShippingMethod());
        assertEquals(orderList.getContent().get(1).getSubtotal(), order2.getSubtotal());
        assertEquals(orderList.getContent().get(1).getTotal(), order2.getTotal());
        assertEquals(orderList.getContent().get(1).getOrderProducts().size(), order2.getOrderProducts().size());
    }

    @Test
    public void whenFindById_thenReturnOrder() {
        Order order = generateOrder(0, "Nick", "Black", 2);

        Order savedOrder = orderRepository.save(order);

        assertEquals(savedOrder.getEmail(), order.getEmail());
        assertEquals(savedOrder.getFirstName(), order.getFirstName());
        assertEquals(savedOrder.getLastName(), order.getLastName());
        assertEquals(savedOrder.getComment(), order.getComment());
        assertEquals(savedOrder.getOrderDate(), order.getOrderDate());
        assertEquals(savedOrder.getPaymentMethod(), order.getPaymentMethod());
        assertEquals(savedOrder.getShippingMethod(), order.getShippingMethod());
        assertEquals(savedOrder.getSubtotal(), order.getSubtotal());
        assertEquals(savedOrder.getTotal(), order.getTotal());
        assertEquals(savedOrder.getOrderProducts().size(), order.getOrderProducts().size());
    }
}