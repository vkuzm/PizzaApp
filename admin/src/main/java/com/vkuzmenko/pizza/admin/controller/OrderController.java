package com.vkuzmenko.pizza.admin.controller;

import com.vkuzmenko.pizza.admin.dto.Order;
import com.vkuzmenko.pizza.admin.service.OrderService;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.PagedResources.PageMetadata;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.vkuzmenko.pizza.admin.Constants.ORDER_URL;
import static com.vkuzmenko.pizza.admin.helper.PaginationHelper.createPagination;
import static com.vkuzmenko.pizza.admin.helper.ResponsesHelper.responseToContent;

@Controller
@RequestMapping(value = ORDER_URL)
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public String index(Pageable pageable, Model model) {
        PagedResources<Resource<Order>> bodyResponse = orderService.findAll(pageable);

        if (bodyResponse != null) {
            PageMetadata metadata = bodyResponse.getMetadata();
            List<Integer> pageNumbers = createPagination(metadata);
            List<Order> orders = responseToContent(bodyResponse.getContent());

            model.addAttribute("pageNumbers", pageNumbers);
            model.addAttribute("orders", orders);
            model.addAttribute("meta", metadata);
        }

        return "order-list";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        Optional<Order> order = orderService.findById(id);

        model.addAttribute("order", order.get());
        model.addAttribute("action", "/admin/orders/edit");

        return "order-form";
    }

    @PostMapping("/edit")
    public String edit(@ModelAttribute Order order) {
        ResponseEntity<String> response = orderService.update(order);

        if (response.getStatusCode() == HttpStatus.OK) {
            return "redirect:/admin/orders/";
        }

        return "order-form";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("order", new Order());
        model.addAttribute("action", "/admin/orders/create");

        return "order-form";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute Order order) {
        ResponseEntity<String> response = orderService.insert(order);

        if (response.getStatusCode() == HttpStatus.CREATED) {
            return "redirect:/admin/orders/";
        }

        return "order-form";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        orderService.delete(id);
        return "redirect:/admin/orders/";
    }

}
