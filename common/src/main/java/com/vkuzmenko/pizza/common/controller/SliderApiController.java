package com.vkuzmenko.pizza.common.controller;

import com.vkuzmenko.pizza.common.domain.Slider;
import com.vkuzmenko.pizza.common.service.SliderService;
import com.vkuzmenko.pizza.common.utils.ResponseEntityTemplate;
import javassist.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

import static com.vkuzmenko.pizza.common.Constants.API_URL;
import static com.vkuzmenko.pizza.common.Constants.API_URL_SLIDER;

@RestController
@SuppressWarnings("Duplicates") // The same code in modules that supposed to be different micro-services.
@RequestMapping(value = API_URL + "/" + API_URL_SLIDER, produces = "application/json")
public class SliderApiController {
    private final SliderService sliderService;

    public SliderApiController(SliderService sliderService) {
        this.sliderService = sliderService;
    }

    @GetMapping
    public ResponseEntity<List<Slider>> findAll() {
        List<Slider> slideList = sliderService.findAll();
        return ResponseEntityTemplate.returnList(slideList);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Slider> findById(@PathVariable("id") Long sliderId) {
        Optional<Slider> slider = sliderService.findById(sliderId);

        if (slider.isPresent()) {
            return new ResponseEntity<>(slider.get(), HttpStatus.OK);
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity create(
            @Valid @RequestBody Slider slider,
            BindingResult errors) {

        if (!errors.hasErrors()) {
            Optional<Slider> savedSlider = sliderService.save(slider);

            if (savedSlider.isPresent()) {
                return new ResponseEntity<>(savedSlider.get(), HttpStatus.CREATED);
            }
        }

        return new ResponseEntity<>(errors.getAllErrors(), HttpStatus.BAD_REQUEST);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity edit(
            @PathVariable("id") Long id,
            @Valid @RequestBody Slider slider,
            BindingResult errors) {

        if (!errors.hasErrors()) {
            Optional<Slider> savedSlider = sliderService.update(id, slider);

            if (savedSlider.isPresent()) {
                return new ResponseEntity<>(savedSlider.get(), HttpStatus.OK);
            }
        }

        return new ResponseEntity<>(errors.getAllErrors(), HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id) {
        try {
            sliderService.delete(id);
        } catch (NotFoundException e) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.noContent().build();
    }

}
