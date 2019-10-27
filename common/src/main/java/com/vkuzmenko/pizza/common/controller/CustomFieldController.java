package com.vkuzmenko.pizza.common.controller;

import com.vkuzmenko.pizza.common.domain.CustomField;
import com.vkuzmenko.pizza.common.repository.CustomFieldRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.vkuzmenko.pizza.common.Constants.API_URL;
import static com.vkuzmenko.pizza.common.Constants.API_URL_CUSTOM_FIELDS;

@RestController
@RequestMapping(value = API_URL + "/" + API_URL_CUSTOM_FIELDS, produces = "application/json")
public class CustomFieldController {
    private final CustomFieldRepository customFieldRepo;

    public CustomFieldController(CustomFieldRepository customFieldRepo) {
        this.customFieldRepo = customFieldRepo;
    }

    @GetMapping
    public List<CustomField> getFields() {
        return customFieldRepo.findAll();
    }

    @PostMapping
    public ResponseEntity<?> saveField(
            @Valid @RequestBody CustomField customField,
            BindingResult bindingResult) {

        if (!bindingResult.hasErrors()) {
            CustomField savedCustomField = customFieldRepo.save(customField);
            if (savedCustomField != null) {
                return new ResponseEntity<>(savedCustomField, HttpStatus.CREATED);
            }
        }

        return new ResponseEntity<>(bindingResult.getAllErrors(), HttpStatus.BAD_REQUEST);
    }

}
