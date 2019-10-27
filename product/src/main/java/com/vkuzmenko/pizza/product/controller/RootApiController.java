package com.vkuzmenko.pizza.product.controller;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.vkuzmenko.pizza.product.Constants.API_URL;

@RestController
@RequestMapping(value = API_URL, produces = "application/json")
public class RootApiController {

    @GetMapping
    public ResourceSupport root() {
        ResourceSupport rootResource = new ResourceSupport();
        rootResource.add(CategoryApiController.rootUrl());
        rootResource.add(ProductApiController.rootUrl());

        return rootResource;
    }
}
