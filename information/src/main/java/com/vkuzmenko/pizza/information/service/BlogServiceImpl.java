package com.vkuzmenko.pizza.information.service;

import com.vkuzmenko.pizza.information.domain.Blog;
import com.vkuzmenko.pizza.information.repository.BlogRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BlogServiceImpl implements BlogService {
    private final BlogRepository blogRepo;

    public BlogServiceImpl(BlogRepository blogRepo) {
        this.blogRepo = blogRepo;
    }

    @Override
    public Page<Blog> findAll(Pageable pageable) {
        Page<Blog> posts = blogRepo.findAll(pageable);

        if (!posts.isEmpty()) {
            posts.forEach(p ->
                    p.setCommentCount(p.getComments().size()));
        }

        return posts;
    }

    @Override
    public Optional<Blog> findById(Long postId) {
        Optional<Blog> post = blogRepo.findById(postId);

        if (post.isPresent()) {
            post.ifPresent(blog ->
                    blog.setCommentCount(blog.getComments().size()));
        }

        return post;
    }

    @Override
    public List<Blog> findLatest(Pageable pageable) {
        List<Blog> latestPosts = blogRepo.findAllByOrderByCreatedAtDesc(pageable);

        if (!latestPosts.isEmpty()) {
            latestPosts.forEach(p ->
                    p.setCommentCount(p.getComments().size()));
        }

        return latestPosts;
    }

    @Override
    public Optional<Blog> save(Blog post) {
        return Optional.of(blogRepo.save(post));
    }

    @Override
    public Optional<Blog> update(Long id, Blog post) {
        Optional<Blog> persistedPost = blogRepo.findById(id);

        if (!persistedPost.isPresent()) {
            throw new RuntimeException("Persisted post ID " + id + " is not existed.");
        }

        persistedPost.ifPresent(p -> {
            if (post.getTitle() != null) {
                p.setTitle(post.getTitle());
            }
            if (post.getImage() != null) {
                p.setImage(post.getImage());
            }
            if (post.getDescription() != null) {
                p.setDescription(post.getDescription());
            }
            if (post.getCreatedAt() != null) {
                p.setCreatedAt(post.getCreatedAt());
            }
            if (post.getShortDescription() != null) {
                p.setShortDescription(post.getShortDescription());
            }
        });

        Blog savedPost = blogRepo.save(persistedPost.get());
        return Optional.of(savedPost);
    }

    @Override
    public void delete(Long id) {
        blogRepo.deleteById(id);
    }
}
