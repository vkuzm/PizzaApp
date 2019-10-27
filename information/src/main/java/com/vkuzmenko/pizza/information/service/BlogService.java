package com.vkuzmenko.pizza.information.service;

import com.vkuzmenko.pizza.information.domain.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface BlogService {
    Page<Blog> findAll(Pageable pageable);

    Optional<Blog> findById(Long postId);

    List<Blog> findLatest(Pageable pageable);

    Optional<Blog> save(Blog post);

    Optional<Blog> update(Long id, Blog post);

    void delete(Long id);
}
