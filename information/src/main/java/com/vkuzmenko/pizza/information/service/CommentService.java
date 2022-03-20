package com.vkuzmenko.pizza.information.service;


import com.vkuzmenko.pizza.information.domain.Comment;

import java.util.Optional;

public interface CommentService {
    Optional<Comment> findById(Long commentId);

    void addComment(Comment comment, Long postId);
}
