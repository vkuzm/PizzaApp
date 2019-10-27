package com.vkuzmenko.pizza.information.resource;

import com.vkuzmenko.pizza.information.controller.BlogApiController;
import com.vkuzmenko.pizza.information.domain.Blog;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class BlogResourceAssembler extends ResourceAssemblerSupport<Blog, BlogResource> {

    public BlogResourceAssembler() {
        super(BlogApiController.class, BlogResource.class);
    }

    @Override
    protected BlogResource instantiateResource(Blog post) {
        return new BlogResource(post);
    }

    @Override
    public BlogResource toResource(Blog post) {
        return createResourceWithId(post.getPostId(), post);
    }
}
