package com.vkuzmenko.pizza.checkout;


import com.vkuzmenko.pizza.checkout.domain.Order;
import com.vkuzmenko.pizza.checkout.domain.OrderProduct;
import com.vkuzmenko.pizza.checkout.dto.CartItemDto;

import java.util.ArrayList;
import java.util.List;

public class Seeds {

    public static Order generateOrder(long orderId, String firstName, String lastName, int productLimit) {
        Order order = new Order();
        if (orderId > 0) {
            order.setOrderId(orderId);
        }
        order.setEmail("test@mail.com");
        order.setComment("test comment");
        order.setFirstName(firstName);
        order.setLastName(lastName);
        order.setOrderDate("12/12/2019");
        order.setPaymentMethod("Visa");
        order.setShippingMethod("Pickup");
        order.setSubtotal(600 * productLimit);
        order.setTotal(600 * productLimit);

        List<OrderProduct> productList = new ArrayList<>();

        for (int i = 1; i <= productLimit; i++) {
            OrderProduct product = new OrderProduct();
            if (orderId > 0) {
                product.setId(i);
            }
            product.setPrice(300);
            product.setProductName("Product name " + i);
            product.setQuantity(2);
            product.setTotal(600);
            product.setPrice(100);
            product.setOrder(order);

            productList.add(product);
        }

        order.setOrderProducts(productList);

        return order;
    }

    public static CartItemDto generateCartItem(long productId, String name, int price) {
        CartItemDto cartItem = new CartItemDto();

        if (productId > 0) {
            cartItem.setProductId(productId);
        }
        cartItem.setName(name);
        cartItem.setImage("image.jpg");
        cartItem.setPrice(price);
        cartItem.setQuantity(2);
        cartItem.setTotal(price * 2);

        return cartItem;
    }

}
