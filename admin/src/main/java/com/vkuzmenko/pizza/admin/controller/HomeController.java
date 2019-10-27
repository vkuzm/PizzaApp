package com.vkuzmenko.pizza.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.vkuzmenko.pizza.admin.Constants.BASE_URL;

@Controller
@RequestMapping(value = BASE_URL)
public class HomeController {

    @GetMapping
    public String index() {
        return "home";
    }
}
