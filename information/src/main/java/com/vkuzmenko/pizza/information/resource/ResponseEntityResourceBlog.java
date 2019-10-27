package com.vkuzmenko.pizza.information.resource;

import com.vkuzmenko.pizza.information.domain.Blog;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.Resources;

import java.util.List;

public class ResponseEntityResourceBlog {

    private ResponseEntityResourceBlog() {
    }

    public static Resources<BlogResource> convert(List<Blog> posts) {
        List<BlogResource> blogResource
                = new BlogResourceAssembler().toResources(posts);

        return new Resources<>(blogResource);
    }

    public static List<BlogResource> convert(Page<Blog> pages) {
        return new BlogResourceAssembler().toResources(pages);
    }

    public static BlogResource convert(Blog blog) {
        return new BlogResourceAssembler().toResource(blog);
    }
}
