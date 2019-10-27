package com.vkuzmenko.pizza.information.controller;

import com.vkuzmenko.pizza.information.domain.Blog;
import com.vkuzmenko.pizza.information.resource.BlogResource;
import com.vkuzmenko.pizza.information.resource.BlogResourceAssembler;
import com.vkuzmenko.pizza.information.service.BlogService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

import static com.vkuzmenko.pizza.information.Constants.*;
import static com.vkuzmenko.pizza.information.resource.ResponseEntityResourceBlog.convert;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@SuppressWarnings("Duplicates") // The same code in modules that supposed to be different micro-services.
@RequestMapping(value = API_URL + "/" + API_URL_BLOG, produces = "application/json")
public class BlogApiController {
    private final BlogService blogService;
    private final BlogResourceAssembler blogResAssembler;

    public BlogApiController(BlogService blogService, BlogResourceAssembler blogResAssembler) {
        this.blogService = blogService;
        this.blogResAssembler = blogResAssembler;
    }

    @GetMapping(produces = "application/hal+json")
    public ResponseEntity<PagedResources<BlogResource>> findAll(
            @PageableDefault(size = BLOG_LIMIT) Pageable pageable,
            PagedResourcesAssembler<Blog> assembler) {

        Page<Blog> blogPages = blogService.findAll(pageable);

        if (!blogPages.isEmpty()) {
            return new ResponseEntity<>(assembler
                    .toResource(blogPages, blogResAssembler, rootUrl()), HttpStatus.OK);
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BlogResource> findById(@PathVariable("id") Long postId) {
        Optional<Blog> post = blogService.findById(postId);

        if (post.isPresent()) {
            BlogResource blogResource = convert(post.get());
            blogResource.add(rootUrl());

            return new ResponseEntity<>(blogResource, HttpStatus.OK);
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/latest")
    public ResponseEntity<Resources<BlogResource>> findLatest(
            @RequestParam(value = "limit", required = false) int limit) {

        List<Blog> latestPosts = blogService.findLatest(
                PageRequest.of(0, limit > 0 ? limit : LATEST_POSTS_LIMIT));

        if (!latestPosts.isEmpty()) {
            Resources<BlogResource> blogResource = convert(latestPosts);
            Resources<BlogResource> blogResources = new Resources<>(blogResource);
            blogResource.add(rootUrl());

            return new ResponseEntity<>(blogResources, HttpStatus.OK);
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> create(
            @Valid @RequestBody Blog post,
            BindingResult errors) {

        if (!errors.hasErrors()) {
            Optional<Blog> savedBlog = blogService.save(post);

            if (savedBlog.isPresent()) {
                return new ResponseEntity<>(savedBlog, HttpStatus.CREATED);
            }
        }

        return new ResponseEntity<>(errors.getAllErrors(), HttpStatus.BAD_REQUEST);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity edit(
            @PathVariable("id") Long id,
            @Valid @RequestBody Blog post,
            BindingResult errors) {

        if (!errors.hasErrors()) {
            Optional<Blog> savedBlog = blogService.update(id, post);

            if (savedBlog.isPresent()) {
                return ResponseEntity.ok().build();
            }
        }

        return new ResponseEntity<>(errors.getAllErrors(), HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id) {
        blogService.delete(id);
        return ResponseEntity.notFound().build();
    }

    public static Link rootUrl() {
        return linkTo(methodOn(BlogApiController.class)
                .findAll(null, null))
                .withRel(API_URL_BLOG);
    }

}
