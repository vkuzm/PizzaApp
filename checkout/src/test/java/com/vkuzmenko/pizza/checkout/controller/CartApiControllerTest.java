package com.vkuzmenko.pizza.checkout.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vkuzmenko.pizza.checkout.dto.CartItemDto;
import com.vkuzmenko.pizza.checkout.service.CartService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static com.vkuzmenko.pizza.checkout.Seeds.generateCartItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(CartApiController.class)
public class CartApiControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private CartService cartService;

    @Autowired
    private ObjectMapper mapper;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(new CartApiController(cartService))
                .build();
    }

    private final String cartTestApiUrl = "http://localhost/api/cart";

    @Test
    public void whenGetCart_thenReturnItemsInCart() throws Exception {
        CartItemDto cartItem1 = generateCartItem(1, "Product 1", 2000);
        CartItemDto cartItem2 = generateCartItem(2, "Product 2", 1000);

        List<CartItemDto> cartItems = Arrays.asList(cartItem1, cartItem2);

        given(cartService.getCart(anyList()))
                .willReturn(cartItems);

        ResultActions result = mockMvc
                .perform(post(cartTestApiUrl)
                        .content(mapper.writeValueAsBytes(cartItems))
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE));

        result
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].productId", is((int) cartItem1.getProductId())))
                .andExpect(jsonPath("$[0].name", is(cartItem1.getName())))
                .andExpect(jsonPath("$[0].quantity", is(cartItem1.getQuantity())))
                .andExpect(jsonPath("$[0].price", is(cartItem1.getPrice())))
                .andExpect(jsonPath("$[1].productId", is((int) cartItem2.getProductId())))
                .andExpect(jsonPath("$[1].name", is(cartItem2.getName())))
                .andExpect(jsonPath("$[1].quantity", is(cartItem2.getQuantity())))
                .andExpect(jsonPath("$[1].price", is(cartItem2.getPrice())));
    }
}