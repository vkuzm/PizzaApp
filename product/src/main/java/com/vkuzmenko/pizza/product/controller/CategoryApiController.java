package com.vkuzmenko.pizza.product.controller;

import com.vkuzmenko.pizza.product.domain.Category;
import com.vkuzmenko.pizza.product.resource.CategoryResource;
import com.vkuzmenko.pizza.product.service.CategoryService;
import javassist.NotFoundException;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

import static com.vkuzmenko.pizza.product.Constants.*;
import static com.vkuzmenko.pizza.product.resource.ResponseEntityResourceCategory.convert;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@SuppressWarnings("Duplicates") // The same code in modules that supposed to be different services.
@RequestMapping(value = API_URL + "/" + API_URL_CATEGORIES, produces = "application/json")
public class CategoryApiController {

    private final CategoryService categoryService;

    public CategoryApiController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping(produces = "application/hal+json")
    public ResponseEntity<Resources<CategoryResource>> findAll() {
        List<Category> categories = categoryService.findAll();

        if (!categories.isEmpty()) {
            Resources<CategoryResource> categoryResources
                    = convert(categories, true);

            categoryResources.add(rootUrl());

            return new ResponseEntity<>(categoryResources, HttpStatus.OK);
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping(value = "/{id}", produces = "application/hal+json")
    public ResponseEntity<CategoryResource> findById(@PathVariable("id") Long categoryId) {
        Optional<Category> category = categoryService.findById(categoryId);

        if (category.isPresent()) {
            CategoryResource categoryResource = convert(category.get());
            categoryResource.add(rootUrl());

            return new ResponseEntity<>(categoryResource, HttpStatus.OK);
        }

        return ResponseEntity.notFound().build();
    }


    @GetMapping(value = "/showcase", produces = "application/hal+json")
    public ResponseEntity<Resources<CategoryResource>> getShowCase(
            @RequestParam(value = "limit", required = false) Integer productLimit) {

        List<Category> categories = categoryService.getShowCase(
                productLimit == null ? SHOWCASE_LIMIT : productLimit);

        if (!categories.isEmpty()) {
            Resources<CategoryResource> categoryResources
                    = convert(categories, true);

            categoryResources.add(CategoryApiController.rootUrl());

            return new ResponseEntity<>(categoryResources, HttpStatus.OK);
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> create(
            @Valid @RequestBody Category category,
            BindingResult bindingResult) {

        if (!bindingResult.hasErrors()) {
            Optional<Category> savedCategory = categoryService.save(category);

            if (savedCategory.isPresent()) {
                CategoryResource categoryResource = convert(savedCategory.get());
                categoryResource.add(rootUrl());

                return new ResponseEntity<>(categoryResource, HttpStatus.CREATED);
            }
        }

        return new ResponseEntity<>(bindingResult.getAllErrors(), HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> edit(
            @PathVariable("id") Long id,
            @Valid @RequestBody Category category,
            BindingResult bindingResult) {

        if (!bindingResult.hasErrors()) {
            Optional<Category> updatedCategory = Optional.empty();

            try {
                updatedCategory = categoryService.update(id, category);
            } catch (NotFoundException e) {
                e.printStackTrace();
            }

            if (updatedCategory.isPresent()) {
                CategoryResource categoryResource = convert(updatedCategory.get());
                categoryResource.add(rootUrl());

                return new ResponseEntity<>(categoryResource, HttpStatus.OK);
            }
        }

        return new ResponseEntity<>(bindingResult.getAllErrors(), HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id) {
        try {
            categoryService.delete(id);
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
        return ResponseEntity.noContent().build();
    }

    public static Link rootUrl() {
        return linkTo(methodOn(CategoryApiController.class)
                .findAll())
                .withRel(API_URL_CATEGORIES);
    }
}
