package com.vkuzmenko.pizza.information.service;

import com.vkuzmenko.pizza.information.domain.Blog;
import com.vkuzmenko.pizza.information.domain.Comment;
import com.vkuzmenko.pizza.information.repository.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {

    private final BlogService blogService;
    private final CommentRepository commentRepo;

    public CommentServiceImpl(BlogService blogService, CommentRepository commentRepo) {
        this.blogService = blogService;
        this.commentRepo = commentRepo;
    }

    @Override
    public Optional<Comment> findById(Long commentId) {
        return commentRepo.findById(commentId);
    }

    @Override
    public void addComment(Comment comment, Long postId) {
        Optional<Blog> blog = blogService.findById(postId);

        if (blog.isPresent()) {
            blog.get().addComment(comment);
            blogService.save(blog.get());
        }
    }
}
