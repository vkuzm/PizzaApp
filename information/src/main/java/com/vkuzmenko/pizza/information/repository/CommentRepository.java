package com.vkuzmenko.pizza.information.repository;

import com.vkuzmenko.pizza.information.domain.Comment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends CrudRepository<Comment, Long> {
}
