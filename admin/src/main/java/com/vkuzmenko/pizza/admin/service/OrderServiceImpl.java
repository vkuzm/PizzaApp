package com.vkuzmenko.pizza.admin.service;

import com.vkuzmenko.pizza.admin.dto.Order;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.vkuzmenko.pizza.admin.Constants.*;
import static com.vkuzmenko.pizza.admin.helper.ResponsesHelper.httpHeaders;

@Service
@SuppressWarnings("Duplicates") // The same code in modules that supposed to be different micro-services.
public class OrderServiceImpl implements OrderService {
    private final RestTemplate restTemplate;

    public OrderServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public PagedResources<Resource<Order>> findAll(Pageable pageable) {
        final String URL = ORDER_SERVICE + "orders?page={page}&size={size}";

        Map<String, Integer> urlParams = new HashMap<>();
        urlParams.put("page", pageable.getPageNumber());
        urlParams.put("size", LIMIT_IN_PAGE);

        final ResponseEntity<PagedResources<Resource<Order>>> response = restTemplate
                .exchange(URL, HttpMethod.GET, null,
                        new ParameterizedTypeReference<PagedResources<Resource<Order>>>() {
                        }, urlParams);

        if (response.getStatusCode() != HttpStatus.OK) {
            throw new RuntimeException("Orders not found");
        }

        return response.getBody();
    }

    @Override
    public Optional<Order> findById(Long orderId) {
        final String URL = ORDER_SERVICE + "orders/{orderId}";

        Map<String, Long> urlParams = new HashMap<>();
        urlParams.put("orderId", orderId);

        ResponseEntity<Order> response =
                new RestTemplate().getForEntity(URL, Order.class, urlParams);

        if (response.getStatusCode() != HttpStatus.OK) {
            throw new RuntimeException("Order not found");
        }

        return Optional.ofNullable(response.getBody());
    }

    @Override
    public ResponseEntity<String> insert(Order order) {
        final String URL = ORDER_SERVICE + "orders";

        HttpEntity<Order> request = new HttpEntity<>(order, httpHeaders());

        return new RestTemplate()
                .postForEntity(URL, request, String.class);
    }

    @Override
    public ResponseEntity<String> update(Order order) {
        final String URL = ORDER_SERVICE + "orders/{orderId}";

        Map<String, Long> urlParams = new HashMap<>();
        urlParams.put("orderId", order.getOrderId());

        HttpEntity<Order> request = new HttpEntity<>(order, httpHeaders());

        return new RestTemplate()
                .exchange(URL, HttpMethod.PUT, request, String.class, urlParams);
    }

    @Override
    public ResponseEntity<String> delete(Long orderId) {
        final String URL = ORDER_SERVICE + "orders/{orderId}";

        Map<String, Long> params = new HashMap<>();
        params.put("orderId", orderId);

        return new RestTemplate()
                .exchange(URL, HttpMethod.DELETE, null, String.class, params);
    }
}
