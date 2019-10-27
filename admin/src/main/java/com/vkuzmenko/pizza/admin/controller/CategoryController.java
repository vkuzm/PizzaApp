package com.vkuzmenko.pizza.admin.controller;

import com.vkuzmenko.pizza.admin.dto.Category;
import com.vkuzmenko.pizza.admin.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.vkuzmenko.pizza.admin.Constants.CATEGORY_URL;

@Controller
@RequestMapping(value = CATEGORY_URL)
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public String index(Model model) {
        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);

        return "category-list";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        Optional<Category> category = categoryService.findById(id);

        model.addAttribute("category", category.get());
        model.addAttribute("action", "/admin/categories/edit");

        return "category-form";
    }

    @PostMapping("/edit")
    public String edit(@ModelAttribute Category category) {
        ResponseEntity<String> response = categoryService.update(category);

        if (response.getStatusCode() == HttpStatus.OK) {
            return "redirect:/admin/categories/";
        }

        return "category-form";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("category", new Category());
        model.addAttribute("action", "/admin/categories/create");

        return "category-form";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute Category category) {
        ResponseEntity<String> response = categoryService.insert(category);

        if (response.getStatusCode() == HttpStatus.CREATED) {
            return "redirect:/admin/categories/";
        }

        return "category-form";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        categoryService.delete(id);

        return "redirect:/admin/categories/";
    }

}
