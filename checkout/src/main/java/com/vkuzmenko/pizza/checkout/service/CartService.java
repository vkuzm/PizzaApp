package com.vkuzmenko.pizza.checkout.service;

import com.vkuzmenko.pizza.checkout.dto.CartItemDto;

import java.util.List;

public interface CartService {
    List<CartItemDto> getCart(List<CartItemDto> cartItems);
}
