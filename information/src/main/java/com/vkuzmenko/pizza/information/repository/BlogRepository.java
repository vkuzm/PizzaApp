package com.vkuzmenko.pizza.information.repository;

import com.vkuzmenko.pizza.information.domain.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BlogRepository extends PagingAndSortingRepository<Blog, Long> {

    Page<Blog> findAll(Pageable pageable);

    Optional<Blog> findById(Long postId);

    List<Blog> findAllByOrderByCreatedAtDesc(Pageable pageable);

}
