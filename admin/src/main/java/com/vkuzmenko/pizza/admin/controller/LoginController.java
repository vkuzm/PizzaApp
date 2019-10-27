package com.vkuzmenko.pizza.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class LoginController {

    @GetMapping("login")
    public String login(
            @RequestParam(value = "error", required = false, defaultValue = "false")  String loginError,
            Model model) {

        model.addAttribute("loginError",
                loginError != null && loginError.equals("true"));

        return "login";
    }
}
