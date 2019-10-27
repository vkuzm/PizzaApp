package com.vkuzmenko.pizza.checkout.resource;

import com.vkuzmenko.pizza.checkout.domain.Order;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.Resources;

import java.util.List;

public class ResponseEntityResourceOrder {

    private ResponseEntityResourceOrder() {
    }

    public static Resources<OrderResource> convert(List<Order> orders) {
        List<OrderResource> orderResources
                = new OrderResourceAssembler().toResources(orders);

        return new Resources<>(orderResources);
    }

    public static List<OrderResource> convert(Page<Order> orders) {
        return new OrderResourceAssembler().toResources(orders);
    }

    public static OrderResource convert(Order order) {
        return new OrderResourceAssembler().toResource(order);
    }
}
