package com.vkuzmenko.pizza.admin.service;

import com.vkuzmenko.pizza.admin.dto.Blog;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface BlogService {
    PagedResources<Resource<Blog>> findAll(Pageable pageable);

    Optional<Blog> findById(Long id);

    ResponseEntity<String> insert(Blog post, MultipartFile imageFile);

    ResponseEntity<String> update(Blog post, MultipartFile imageFile);

    ResponseEntity<String> delete(Long id);
}
