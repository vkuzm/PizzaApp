package com.vkuzmenko.pizza.common.repository;

import com.vkuzmenko.pizza.common.domain.Slider;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SliderRepository extends CrudRepository<Slider, Long> {
    List<Slider> findAll();
}
