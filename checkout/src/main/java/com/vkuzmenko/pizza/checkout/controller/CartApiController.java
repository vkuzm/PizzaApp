package com.vkuzmenko.pizza.checkout.controller;

import com.vkuzmenko.pizza.checkout.dto.CartItemDto;
import com.vkuzmenko.pizza.checkout.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/cart", produces = "application/json")
public class CartApiController {
    private final CartService cartService;

    public CartApiController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping
    public ResponseEntity<List<CartItemDto>> getCart(
            @RequestBody List<CartItemDto> cartItems) {

        if (!cartItems.isEmpty()) {
            List<CartItemDto> products = cartService.getCart(cartItems);

            if (products != null && !products.isEmpty()) {
                return new ResponseEntity<>(products, HttpStatus.OK);
            }
        }

        return ResponseEntity.notFound().build();
    }
}
