package com.vkuzmenko.pizza.admin.service;

import com.vkuzmenko.pizza.admin.dto.Blog;
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

import static com.vkuzmenko.pizza.admin.Constants.BlOG_SERVICE;
import static com.vkuzmenko.pizza.admin.Constants.LIMIT_IN_PAGE;
import static com.vkuzmenko.pizza.admin.helper.ResponsesHelper.httpHeaders;

@Service
@SuppressWarnings("Duplicates") // The same code in modules that supposed to be different micro-services.
public class BlogServiceImpl implements BlogService {
    private final RestTemplate restTemplate;

    public BlogServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public PagedResources<Resource<Blog>> findAll(Pageable pageable) {
        final String url = BlOG_SERVICE + "blog?page={page}&size={size}";

        Map<String, Integer> urlParams = new HashMap<>();
        urlParams.put("page", pageable.getPageNumber());
        urlParams.put("size", LIMIT_IN_PAGE);

        final ResponseEntity<PagedResources<Resource<Blog>>> response = restTemplate
                .exchange(url, HttpMethod.GET, null,
                        new ParameterizedTypeReference<PagedResources<Resource<Blog>>>() {
                        }, urlParams);

        if (response.getStatusCode() != HttpStatus.OK) {
            throw new RuntimeException("Posts not found");
        }

        return response.getBody();
    }

    @Override
    public Optional<Blog> findById(Long id) {
        final String url = BlOG_SERVICE + "blog/{id}";

        Map<String, Long> urlParams = new HashMap<>();
        urlParams.put("id", id);

        ResponseEntity<Blog> response =
                new RestTemplate().getForEntity(url, Blog.class, urlParams);

        if (response.getStatusCode() != HttpStatus.OK) {
            throw new RuntimeException("Post not found");
        }

        return Optional.ofNullable(response.getBody());
    }

    @Override
    public ResponseEntity<String> insert(Blog post, MultipartFile imageFile) {
        final String url = BlOG_SERVICE + "blog";

        HttpEntity<Blog> request
                = new HttpEntity<>(post, httpHeaders());

        return new RestTemplate()
                .postForEntity(url, request, String.class);
    }

    @Override
    public ResponseEntity<String> update(Blog post, MultipartFile imageFile) {
        final String url = BlOG_SERVICE + "blog/{id}";

        Map<String, Long> urlParams = new HashMap<>();
        urlParams.put("id", post.getPostId());

        HttpEntity<Blog> request = new HttpEntity<>(post, httpHeaders());

        return new RestTemplate()
                .exchange(url, HttpMethod.PUT, request, String.class, urlParams);
    }

    @Override
    public ResponseEntity<String> delete(Long id) {
        final String url = BlOG_SERVICE + "blog/{id}";

        Map<String, Long> params = new HashMap<>();
        params.put("id", id);

        return new RestTemplate()
                .exchange(url, HttpMethod.DELETE, null, String.class, params);
    }
}
