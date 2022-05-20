package com.vkuzmenko.pizza.checkout.service;

import com.vkuzmenko.pizza.checkout.dto.CartItemDto;
import com.vkuzmenko.pizza.checkout.dto.ProductDto;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {
    private final RestTemplate restTemplate;

    public CartServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public List<CartItemDto> getCart(List<CartItemDto> cartItemList) {
        if (!cartItemList.isEmpty()) {
            for (CartItemDto cartItem : cartItemList) {
                if (cartItem.getProductId() > 0) {
                    ProductDto product = restTemplate
                            .getForObject("http://gateway-service:8080/product-service/api/products/" +
                                    cartItem.getProductId(), ProductDto.class);

                    if (product != null) {
                        cartItem.setName(product.getName());
                        cartItem.setImage(product.getImage());
                        cartItem.setShortDescription(product.getShortDescription());
                        cartItem.setPrice(product.getPrice());
                        cartItem.setTotal(product.getPrice() * cartItem.getQuantity());
                    }
                }
            }

            return cartItemList;
        }

        return null;
    }
}
