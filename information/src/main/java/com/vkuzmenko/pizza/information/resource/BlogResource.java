package com.vkuzmenko.pizza.information.resource;

import com.vkuzmenko.pizza.information.domain.Blog;
import com.vkuzmenko.pizza.information.domain.Comment;
import lombok.Getter;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

import java.util.List;

@Relation(value = "blog", collectionRelation = "posts")
public class BlogResource extends ResourceSupport {

    @Getter
    private final long postId;

    @Getter
    private final String title;

    @Getter
    private final String description;

    @Getter
    private final String shortDescription;

    @Getter
    private final String image;

    @Getter
    private final String createdAt;

    @Getter
    private final List<Comment> comments;

    @Getter
    private final Integer commentCount;

    public BlogResource(Blog post) {
        this.postId = post.getPostId();
        this.title = post.getTitle();
        this.description = post.getDescription();
        this.shortDescription = post.getShortDescription();
        this.image = post.getImage();
        this.createdAt = post.getCreatedAt();
        this.comments = post.getComments();
        this.commentCount = post.getCommentCount();
    }
}