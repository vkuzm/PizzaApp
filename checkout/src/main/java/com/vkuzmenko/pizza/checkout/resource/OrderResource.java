package com.vkuzmenko.pizza.checkout.resource;

import com.vkuzmenko.pizza.checkout.domain.Order;
import com.vkuzmenko.pizza.checkout.domain.OrderProduct;
import lombok.Getter;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

import java.util.List;

@Relation(value = "orders", collectionRelation = "orders")
public class OrderResource extends ResourceSupport {

    @Getter
    private final long orderId;

    @Getter
    private final String email;

    @Getter
    private final String firstName;

    @Getter
    private final String lastName;

    @Getter
    private final String comment;

    @Getter
    private final String paymentMethod;

    @Getter
    private final String shippingMethod;

    @Getter
    private final int subtotal;

    @Getter
    private final int total;

    @Getter
    private final String orderDate;

    @Getter
    private final List<OrderProduct> orderProducts;

    public OrderResource(Order order) {
        this.orderId = order.getOrderId();
        this.email = order.getEmail();
        this.firstName = order.getFirstName();
        this.lastName = order.getLastName();
        this.comment = order.getComment();
        this.paymentMethod = order.getPaymentMethod();
        this.shippingMethod = order.getShippingMethod();
        this.subtotal = order.getSubtotal();
        this.total = order.getTotal();
        this.orderDate = order.getOrderDate();
        this.orderProducts = order.getOrderProducts();
    }
}