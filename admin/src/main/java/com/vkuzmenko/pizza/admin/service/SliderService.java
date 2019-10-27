package com.vkuzmenko.pizza.admin.service;

import com.vkuzmenko.pizza.admin.dto.Slider;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface SliderService {
    List<Slider> findAll();

    Optional<Slider> findById(Long id);

    ResponseEntity<String> insert(Slider slider, MultipartFile imageFile);

    ResponseEntity<String> update(Slider slider, MultipartFile imageFile);

    ResponseEntity<String> delete(Long id);
}
