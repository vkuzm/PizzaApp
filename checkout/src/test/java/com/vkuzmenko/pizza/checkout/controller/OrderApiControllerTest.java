package com.vkuzmenko.pizza.checkout.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vkuzmenko.pizza.checkout.domain.Order;
import com.vkuzmenko.pizza.checkout.domain.OrderProduct;
import com.vkuzmenko.pizza.checkout.helper.PageBuilder;
import com.vkuzmenko.pizza.checkout.resource.OrderResourceAssembler;
import com.vkuzmenko.pizza.checkout.service.OrderService;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.vkuzmenko.pizza.checkout.Seeds.generateOrder;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(OrderApiController.class)
public class OrderApiControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @MockBean
    private OrderResourceAssembler orderResourceAssembler;

    @Autowired
    private ObjectMapper mapper;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(new OrderApiController(orderService, orderResourceAssembler))
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers((ViewResolver) (viewName, locale)
                        -> new MappingJackson2JsonView())
                .build();
    }

    private final String checkoutTestApiUrl = "http://localhost/api/orders";

    @Test
    @Ignore
    public void whenFindAll_thenReturnProductList() throws Exception {
        Order order1 = generateOrder(1, "Nick", "Black", 2);
        Order order2 = generateOrder(2, "Victor", "Strange", 5);
        Order order3 = generateOrder(3, "Max", "White", 1);

        List<Order> orders = Arrays.asList(order1, order2, order3);
        Page<Order> orderPages = new PageBuilder<Order>()
                .elements(orders)
                .pageRequest(PageRequest.of(0, 10))
                .totalElements(orders.size())
                .build();

        given(orderService.findAll(any(Pageable.class)))
                .willReturn(orderPages);

        ResultActions result = mockMvc
                .perform(get(checkoutTestApiUrl).accept(MediaTypes.HAL_JSON_UTF8_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_UTF8_VALUE))
                .andExpect(jsonPath("links[0].href", is(checkoutTestApiUrl + "?page=0&size=10")))
                .andExpect(jsonPath("page.size", is(10)))
                .andExpect(jsonPath("page.totalElements", is(3)))
                .andExpect(jsonPath("page.totalPages", is(1)))
                .andExpect(jsonPath("page.number", is(0)));

        verifyMultipleJson(result, order1, order2, order3);
    }

    @Test
    public void whenFindById_thenReturnOrder() throws Exception {
        Order order = generateOrder(1, "Nick", "Black", 2);

        given(orderService.findById(anyLong()))
                .willReturn(Optional.of(order));

        ResultActions result = mockMvc
                .perform(get(checkoutTestApiUrl + "/" + order.getOrderId())
                        .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE));

        verifySingleJson(result, order);
    }


    private void verifySingleJson(final ResultActions action, final Order order) throws Exception {
        action
                .andExpect(jsonPath("orderId", is((int) order.getOrderId())))
                .andExpect(jsonPath("firstName", is(order.getFirstName())))
                .andExpect(jsonPath("lastName", is(order.getLastName())))
                .andExpect(jsonPath("email", is(order.getEmail())))
                .andExpect(jsonPath("comment", is(order.getComment())))
                .andExpect(jsonPath("orderDate", is(order.getOrderDate())))
                .andExpect(jsonPath("paymentMethod", is(order.getPaymentMethod())))
                .andExpect(jsonPath("shippingMethod", is(order.getShippingMethod())))
                .andExpect(jsonPath("subtotal", is(order.getSubtotal())))
                .andExpect(jsonPath("total", is(order.getTotal())));

        for (int j = 0; j < order.getOrderProducts().size(); j++) {
            List<OrderProduct> products = order.getOrderProducts();

            action
                    .andExpect(jsonPath("orderProducts[" + j + "].id", is((int) products.get(j).getId())))
                    .andExpect(jsonPath("orderProducts[" + j + "].productName", is(products.get(j).getProductName())))
                    .andExpect(jsonPath("orderProducts[" + j + "].quantity", is(products.get(j).getQuantity())))
                    .andExpect(jsonPath("orderProducts[" + j + "].price", is(products.get(j).getPrice())))
                    .andExpect(jsonPath("orderProducts[" + j + "].total", is(products.get(j).getTotal())));
        }
    }

    private void verifyMultipleJson(final ResultActions action, final Order... orders) throws Exception {
        for (int i = 0; i < orders.length; i++) {
            action
                    .andExpect(jsonPath("orderId", is((int) orders[i].getOrderId())))
                    .andExpect(jsonPath("firstName", is(orders[i].getFirstName())))
                    .andExpect(jsonPath("lastName", is(orders[i].getLastName())))
                    .andExpect(jsonPath("email", is(orders[i].getEmail())))
                    .andExpect(jsonPath("comment", is(orders[i].getComment())))
                    .andExpect(jsonPath("orderDate", is(orders[i].getOrderDate())))
                    .andExpect(jsonPath("paymentMethod", is(orders[i].getPaymentMethod())))
                    .andExpect(jsonPath("shippingMethod", is(orders[i].getShippingMethod())))
                    .andExpect(jsonPath("subtotal", is(orders[i].getSubtotal())))
                    .andExpect(jsonPath("total", is(orders[i].getTotal())));

            for (int j = 0; j < orders[i].getOrderProducts().size(); j++) {
                List<OrderProduct> products = orders[i].getOrderProducts();

                action
                        .andExpect(jsonPath("orderProducts[" + j + "].id", is((int) products.get(j).getId())))
                        .andExpect(jsonPath("orderProducts[" + j + "].productName", is(products.get(j).getProductName())))
                        .andExpect(jsonPath("orderProducts[" + j + "].quantity", is(products.get(j).getQuantity())))
                        .andExpect(jsonPath("orderProducts[" + j + "].price", is(products.get(j).getPrice())))
                        .andExpect(jsonPath("orderProducts[" + j + "].total", is(products.get(j).getTotal())));
            }
        }
    }
}