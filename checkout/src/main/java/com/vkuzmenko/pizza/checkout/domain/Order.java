package com.vkuzmenko.pizza.checkout.domain;

import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long orderId;

    @Size(min = 3, max = 20, message = "First name must be between 3 - 20 chars")
    private String firstName;

    @Size(min = 3, max = 20, message = "Last name must be between 3 - 20 chars")
    private String lastName;

    @NotEmpty(message = "Email must not be empty")
    @Email(message = "Email must be correct")
    private String email;

    @Size(max = 50, message = "Comment must be less then 50 chars")
    private String comment;
    private String orderDate;
    private Integer subtotal;
    private Integer total;

    @NotEmpty(message = "Shipping method must not be empty")
    private String shippingMethod;

    @NotEmpty(message = "Payment method must not be empty")
    private String paymentMethod;

    @OneToMany(
            mappedBy = "order",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @Fetch(FetchMode.SUBSELECT)
    private List<OrderProduct> orderProducts = new ArrayList<>();

    public void addsOrderProduct(OrderProduct product) {
        orderProducts.add(product);
        product.setOrder(this);
    }
}
