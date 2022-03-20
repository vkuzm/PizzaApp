package com.vkuzmenko.pizza.common.service;

import com.vkuzmenko.pizza.common.domain.Slider;
import javassist.NotFoundException;

import java.util.List;
import java.util.Optional;

public interface SliderService {
    List<Slider> findAll();

    Optional<Slider> findById(Long id);

    Optional<Slider> save(Slider slider);

    Optional<Slider> update(Long id, Slider slider);

    void delete(Long id) throws NotFoundException;
}
