package com.vkuzmenko.pizza.information.controller;

import com.vkuzmenko.pizza.information.domain.Comment;
import com.vkuzmenko.pizza.information.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.vkuzmenko.pizza.information.Constants.API_URL;
import static com.vkuzmenko.pizza.information.Constants.API_URL_BLOG;

@RestController
@RequestMapping(value = API_URL + "/" + API_URL_BLOG, produces = "application/json")
public class CommentApiController {
    private final CommentService commentService;

    public CommentApiController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/{postId}/comments")
    public ResponseEntity createComment(
            @PathVariable(value = "postId") Long postId,
            @Valid @RequestBody Comment comment) {

        commentService.addComment(comment, postId);

        return new ResponseEntity(null, HttpStatus.CREATED);
    }

}
