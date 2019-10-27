package com.vkuzmenko.pizza.information.controller;

import com.vkuzmenko.pizza.information.domain.Page;
import com.vkuzmenko.pizza.information.repository.PageRepository;
import com.vkuzmenko.pizza.information.resource.PageResource;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

import static com.vkuzmenko.pizza.information.Constants.API_URL;
import static com.vkuzmenko.pizza.information.Constants.API_URL_PAGES;
import static com.vkuzmenko.pizza.information.resource.ResponseEntityResourcePage.convert;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@SuppressWarnings("Duplicates") // The same code in modules that supposed to be different micro-services.
@RequestMapping(value = API_URL + "/" + API_URL_PAGES, produces = "application/json")
public class PageApiController {
    private final PageRepository pageRepo;

    public PageApiController(PageRepository pageRepo) {
        this.pageRepo = pageRepo;
    }

    @GetMapping
    public ResponseEntity<Resources<PageResource>> findAll() {
        List<Page> pages = pageRepo.findAll();

        if (!pages.isEmpty()) {
            Resources<PageResource> pageResource = convert(pages);
            Resources<PageResource> pageResources = new Resources<>(pageResource);
            pageResources.add(rootUrl());

            return new ResponseEntity<>(pageResources, HttpStatus.OK);
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PageResource> findById(@PathVariable("id") Long id) {
        Optional<Page> page = pageRepo.findById(id);

        if (page.isPresent()) {
            PageResource pageResource = convert(page.get());
            pageResource.add(rootUrl());

            return new ResponseEntity<>(pageResource, HttpStatus.OK);
        }

        return ResponseEntity.notFound().build();
    }

    public static Link rootUrl() {
        return linkTo(methodOn(PageApiController.class)
                .findAll())
                .withRel(API_URL_PAGES);
    }

}
