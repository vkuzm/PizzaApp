package com.vkuzmenko.pizza.checkout.resource;

import com.vkuzmenko.pizza.checkout.controller.OrderApiController;
import com.vkuzmenko.pizza.checkout.domain.Order;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class OrderResourceAssembler extends ResourceAssemblerSupport<Order, OrderResource> {

    public OrderResourceAssembler() {
        super(OrderApiController.class, OrderResource.class);
    }

    @Override
    protected OrderResource instantiateResource(Order order) {
        return new OrderResource(order);
    }

    @Override
    public OrderResource toResource(Order order) {
        return createResourceWithId(order.getOrderId(), order);
    }
}
