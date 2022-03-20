package com.vkuzmenko.pizza.admin.service;

import com.vkuzmenko.pizza.admin.dto.Category;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

import static com.vkuzmenko.pizza.admin.Constants.PRODUCT_SERVICE;
import static com.vkuzmenko.pizza.admin.helper.ResponsesHelper.httpHeaders;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final RestTemplate restTemplate;

    public CategoryServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public List<Category> findAll() {
        final String URL = PRODUCT_SERVICE + "categories";

        List<Category> categories = new ArrayList<>();

        final ResponseEntity<Resources<Resource<Category>>> response = restTemplate
                .exchange(URL, HttpMethod.GET, null,
                        new ParameterizedTypeReference<Resources<Resource<Category>>>() {
                        });

        Resources<Resource<Category>> bodyResponse = response.getBody();

        if (bodyResponse != null) {
            categories = bodyResponse.getContent().stream()
                    .map(Resource::getContent)
                    .collect(Collectors.toList());
        }

        return categories;
    }

    @Override
    public Optional<Category> findById(Long categoryId) {
        final String URL = PRODUCT_SERVICE + "categories/{id}";

        Map<String, Long> urlParams = new HashMap<>();
        urlParams.put("id", categoryId);

        ResponseEntity<Category> response =
                restTemplate.getForEntity(URL, Category.class, urlParams);

        return Optional.ofNullable(response.getBody());
    }

    @Override
    public ResponseEntity<String> insert(Category category) {
        final String URL = PRODUCT_SERVICE + "categories";

        HttpEntity<Category> request = new HttpEntity<>(category, httpHeaders());

        return new RestTemplate()
                .postForEntity(URL, request, String.class);
    }

    @Override
    public ResponseEntity<String> update(Category category) {
        final String URL = PRODUCT_SERVICE + "categories/{id}";

        Map<String, Long> urlParams = new HashMap<>();
        urlParams.put("id", category.getCategoryId());

        HttpEntity<Category> request = new HttpEntity<>(category, httpHeaders());

        return new RestTemplate()
                .exchange(URL, HttpMethod.PUT, request, String.class, urlParams);
    }

    @Override
    public ResponseEntity<String> delete(Long id) {
        final String URL = PRODUCT_SERVICE + "categories/{id}";

        Map<String, Long> params = new HashMap<>();
        params.put("id", id);

        return new RestTemplate()
                .exchange(URL, HttpMethod.DELETE, null, String.class, params);
    }
}
