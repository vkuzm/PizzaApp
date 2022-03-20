package com.vkuzmenko.pizza.admin.controller;

import com.vkuzmenko.pizza.admin.dto.Blog;
import com.vkuzmenko.pizza.admin.service.BlogService;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

import static com.vkuzmenko.pizza.admin.Constants.BLOG_URL;
import static com.vkuzmenko.pizza.admin.helper.PaginationHelper.createPagination;
import static com.vkuzmenko.pizza.admin.helper.ResponsesHelper.responseToContent;

@Controller
@RequestMapping(value = BLOG_URL)
public class BlogController {

    private final BlogService blogService;

    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }

    @GetMapping
    public String index(Pageable pageable, Model model) {
        PagedResources<Resource<Blog>> bodyResponse = blogService.findAll(pageable);

        if (bodyResponse != null) {
            PagedResources.PageMetadata metadata = bodyResponse.getMetadata();
            List<Integer> pageNumbers = createPagination(metadata);
            List<Blog> posts = responseToContent(bodyResponse.getContent());

            model.addAttribute("pageNumbers", pageNumbers);
            model.addAttribute("posts", posts);
            model.addAttribute("meta", metadata);
        }

        return "blog-list";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        Optional<Blog> post = blogService.findById(id);

        if (post.isPresent()) {
            model.addAttribute("post", post.get());
            model.addAttribute("action", "/admin/blog/edit");
        }

        return "blog-form";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("post", new Blog());
        model.addAttribute("action", "/admin/blog/create");

        return "blog-form";
    }

    @PostMapping("/create")
    public String create(
            @ModelAttribute Blog post,
            @RequestParam("imageFile") MultipartFile imageFile) {

        ResponseEntity<String> response = blogService.insert(post, imageFile);
        if (response.getStatusCode() == HttpStatus.CREATED) {
            return "redirect:/admin/blog/";
        }

        return "blog-form";
    }

    @PostMapping("/edit")
    public String edit(
            @ModelAttribute Blog post,
            @RequestParam("imageFile") MultipartFile imageFile) {

        ResponseEntity<String> response = blogService.update(post, imageFile);
        if (response.getStatusCode() == HttpStatus.OK) {
            return "redirect:/admin/blog/";
        }

        return "blog-form";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        blogService.delete(id);

        return "redirect:/admin/blog/";
    }
}
