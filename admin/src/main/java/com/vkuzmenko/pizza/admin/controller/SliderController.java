package com.vkuzmenko.pizza.admin.controller;

import com.vkuzmenko.pizza.admin.dto.Slider;
import com.vkuzmenko.pizza.admin.service.SliderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

import static com.vkuzmenko.pizza.admin.Constants.SLIDER_URL;

@Controller
@RequestMapping(value = SLIDER_URL)
public class SliderController {

    private final SliderService sliderService;

    public SliderController(SliderService sliderService) {
        this.sliderService = sliderService;
    }

    @GetMapping
    public String index(Model model) {
        List<Slider> sliders = sliderService.findAll();

        model.addAttribute("slider", sliders);

        return "slider-list";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        Optional<Slider> slider = sliderService.findById(id);

        model.addAttribute("slider", slider.get());
        model.addAttribute("action", "/admin/slider/edit");

        return "slider-form";
    }

    @PostMapping("/edit")
    public String edit(
            @ModelAttribute Slider slider,
            @RequestParam("imageFile") MultipartFile imageFile) {

        ResponseEntity<String> response =
                sliderService.update(slider, imageFile);

        if (response.getStatusCode() == HttpStatus.OK) {
            return "redirect:/admin/slider/";
        }

        return "slider-form";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("slider", new Slider());
        model.addAttribute("action", "/admin/slider/create");

        return "slider-form";
    }

    @PostMapping("/create")
    public String create(
            @ModelAttribute Slider slider,
            @RequestParam("imageFile") MultipartFile imageFile) {

        ResponseEntity<String> response =
                sliderService.insert(slider, imageFile);

        if (response.getStatusCode() == HttpStatus.CREATED) {
            return "redirect:/admin/slider/";
        }

        return "slider-form";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        sliderService.delete(id);

        return "redirect:/admin/slider/";
    }
}