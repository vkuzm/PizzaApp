package com.vkuzmenko.pizza.admin.controller;

import com.vkuzmenko.pizza.admin.dto.Category;
import com.vkuzmenko.pizza.admin.dto.Product;
import com.vkuzmenko.pizza.admin.service.CategoryService;
import com.vkuzmenko.pizza.admin.service.ProductService;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.PagedResources.PageMetadata;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

import static com.vkuzmenko.pizza.admin.Constants.PRODUCT_URL;
import static com.vkuzmenko.pizza.admin.helper.PaginationHelper.createPagination;
import static com.vkuzmenko.pizza.admin.helper.ResponsesHelper.responseToContent;

@Controller
@RequestMapping(value = PRODUCT_URL)
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;

    public ProductController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping
    public String index(Pageable pageable, Model model) {
        PagedResources<Resource<Product>> bodyResponse = productService.findAll(pageable);

        if (bodyResponse != null) {
            PageMetadata metadata = bodyResponse.getMetadata();
            List<Integer> pageNumbers = createPagination(metadata);
            List<Product> products = responseToContent(bodyResponse.getContent());

            model.addAttribute("pageNumbers", pageNumbers);
            model.addAttribute("products", products);
            model.addAttribute("meta", metadata);
        }

        return "product-list";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        Optional<Product> product = productService.findById(id);
        List<Category> categories = categoryService.findAll();

        model.addAttribute("product", product.get());
        model.addAttribute("action", "/admin/products/edit");
        model.addAttribute("categories", categories);

        return "product-form";
    }

    @PostMapping("/edit")
    public String edit(
            @ModelAttribute Product product,
            @RequestParam("imageFile") MultipartFile imageFile) {

        ResponseEntity<String> response =
                productService.update(product, imageFile);

        if (response.getStatusCode() == HttpStatus.OK) {
            return "redirect:/admin/products/";
        }

        return "product-form";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        List<Category> categories = categoryService.findAll();

        model.addAttribute("product", new Product());
        model.addAttribute("action", "/admin/products/create");
        model.addAttribute("categories", categories);

        return "product-form";
    }

    @PostMapping("/create")
    public String create(
            @ModelAttribute Product product,
            @RequestParam("imageFile") MultipartFile imageFile) {

        ResponseEntity<String> response =
                productService.insert(product, imageFile);

        if (response.getStatusCode() == HttpStatus.CREATED) {
            return "redirect:/admin/products/";
        }

        return "product-form";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        productService.delete(id);

        return "redirect:/admin/products/";
    }

}
