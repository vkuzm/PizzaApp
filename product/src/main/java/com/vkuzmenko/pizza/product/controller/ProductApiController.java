package com.vkuzmenko.pizza.product.controller;

import com.vkuzmenko.pizza.product.domain.Product;
import com.vkuzmenko.pizza.product.resource.ProductResource;
import com.vkuzmenko.pizza.product.resource.ProductResourceAssembler;
import com.vkuzmenko.pizza.product.service.ProductService;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;
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

import static com.vkuzmenko.pizza.product.Constants.*;
import static com.vkuzmenko.pizza.product.resource.ResponseEntityResourceProduct.convert;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@SuppressWarnings("Duplicates") // The same code in modules that supposed to be different services.
@RequestMapping(value = API_URL + "/" + API_URL_PRODUCTS, produces = "application/json")
public class ProductApiController {

    private final ProductService productService;
    private final ProductResourceAssembler prodResAssembler;

    public ProductApiController(
            ProductService productService,
            ProductResourceAssembler prodResAssembler) {

        this.productService = productService;
        this.prodResAssembler = prodResAssembler;
    }

    @GetMapping(produces = "application/hal+json")
    public ResponseEntity<PagedResources<ProductResource>> findAll(
            @PageableDefault(size = PRODUCTS_LIMIT) Pageable pageable,
            PagedResourcesAssembler<Product> assembler) {

        Page<Product> products = productService.findAll(pageable);

        if (!products.isEmpty()) {
            return new ResponseEntity<>(assembler
                    .toResource(products, prodResAssembler, rootUrl()), HttpStatus.OK);
        }

        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/{id}", produces = "application/hal+json")
    public ResponseEntity<ProductResource> findById(@PathVariable("id") Long productId) {
        Optional<Product> product = productService.findById(productId);

        if (product.isPresent()) {
            ProductResource productResource = convert(product.get());
            productResource.add(rootUrl());

            return new ResponseEntity<>(productResource, HttpStatus.OK);
        }

        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/top", produces = "application/hal+json")
    public ResponseEntity<Resources<ProductResource>> topProducts() {
        List<Product> topProducts = productService.findAllByHot();

        return responseEntitiesGenerate(topProducts);
    }

    @PostMapping
    public ResponseEntity<?> create(
            @Valid @RequestBody Product product,
            BindingResult errors) {

        if (!errors.hasErrors()) {
            Optional<Product> createdProduct = productService.save(product);

            if (createdProduct.isPresent()) {
                ProductResource productResource = convert(createdProduct.get());
                productResource.add(rootUrl());

                return new ResponseEntity<>(productResource, HttpStatus.CREATED);
            }
        }

        return new ResponseEntity<>(errors.getAllErrors(), HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> edit(
            @PathVariable("id") Long id,
            @Valid @RequestBody Product product,
            BindingResult errors) {

        if (!errors.hasErrors()) {
            Optional<Product> updatedProduct = Optional.empty();

            try {
                updatedProduct = productService.update(id, product);
            } catch (NotFoundException e) {
                e.printStackTrace();
            }

            if (updatedProduct.isPresent()) {
                ProductResource productResource = convert(updatedProduct.get());
                productResource.add(rootUrl());

                return new ResponseEntity<>(productResource, HttpStatus.OK);
            }
        }

        return new ResponseEntity<>(errors.getAllErrors(), HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        try {
            productService.delete(id);
        } catch (NotFoundException e) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.noContent().build();
    }

    private ResponseEntity<Resources<ProductResource>> responseEntitiesGenerate(List<Product> products) {
        if (!products.isEmpty()) {
            Resources<ProductResource> productResources = convert(products);
            productResources.add(rootUrl());

            return new ResponseEntity<>(productResources, HttpStatus.OK);
        }

        return ResponseEntity.notFound().build();
    }

    public static Link rootUrl() {
        return linkTo(methodOn(ProductApiController.class)
                .findAll(null, null))
                .withRel(API_URL_PRODUCTS);
    }
}
