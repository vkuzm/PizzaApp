package com.vkuzmenko.pizza.admin.service;

import com.vkuzmenko.pizza.admin.dto.Product;
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
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.vkuzmenko.pizza.admin.Constants.LIMIT_IN_PAGE;
import static com.vkuzmenko.pizza.admin.Constants.PRODUCT_SERVICE;
import static com.vkuzmenko.pizza.admin.helper.ResponsesHelper.httpHeaders;

@Service
public class ProductServiceImpl implements ProductService {
    private final RestTemplate restTemplate;

    public ProductServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    @SuppressWarnings("Duplicates") // The same code in modules that supposed to be different micro-services.
    public PagedResources<Resource<Product>> findAll(Pageable pageable) {
        final String URL = PRODUCT_SERVICE + "products?page={page}&size={size}";

        Map<String, Integer> urlParams = new HashMap<>();
        urlParams.put("page", pageable.getPageNumber());
        urlParams.put("size", LIMIT_IN_PAGE);

        final ResponseEntity<PagedResources<Resource<Product>>> response = restTemplate
                .exchange(URL, HttpMethod.GET, null,
                        new ParameterizedTypeReference<PagedResources<Resource<Product>>>() {
                        }, urlParams);


        if (response.getStatusCode() != HttpStatus.OK) {
            throw new RuntimeException("Products not found");
        }

        return response.getBody();
    }

    @Override
    public Optional<Product> findById(Long id) {
        final String URL = PRODUCT_SERVICE + "products/{id}";

        Map<String, Long> urlParams = new HashMap<>();
        urlParams.put("id", id);

        ResponseEntity<Product> response =
                new RestTemplate().getForEntity(URL, Product.class, urlParams);

        if (response.getStatusCode() != HttpStatus.OK) {
            throw new RuntimeException("Product not found");
        }

        return Optional.ofNullable(response.getBody());
    }

    @Override
    public ResponseEntity<String> insert(Product product, MultipartFile imageFile) {
        final String URL = PRODUCT_SERVICE + "products";

        // Add image
        // Save file imageFile to storage
        // Get url
        // Product.setImage(url);

        HttpEntity<Product> request = new HttpEntity<>(product, httpHeaders());

        return new RestTemplate()
                .postForEntity(URL, request, String.class);
    }

    @Override
    public ResponseEntity<String> update(Product product, MultipartFile imageFile) {
        final String URL = PRODUCT_SERVICE + "products/{id}";

        // Add image
        // Save file imageFile to storage
        // Get url
        // Product.setImage(url);

        Map<String, Long> urlParams = new HashMap<>();
        urlParams.put("id", product.getProductId());

        HttpEntity<Product> request = new HttpEntity<>(product, httpHeaders());

        return new RestTemplate()
                .exchange(URL, HttpMethod.PUT, request, String.class, urlParams);
    }

    @Override
    public ResponseEntity<String> delete(Long id) {
        final String URL = PRODUCT_SERVICE + "products/{id}";

        Map<String, Long> params = new HashMap<>();
        params.put("id", id);

        return new RestTemplate()
                .exchange(URL, HttpMethod.DELETE, null, String.class, params);
    }
}
