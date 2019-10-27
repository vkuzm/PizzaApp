package com.vkuzmenko.pizza.common.service;

import com.vkuzmenko.pizza.common.domain.Slider;
import com.vkuzmenko.pizza.common.repository.SliderRepository;
import javassist.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SliderServiceImpl implements SliderService{

    private final SliderRepository sliderRepository;

    public SliderServiceImpl(SliderRepository sliderRepository) {
        this.sliderRepository = sliderRepository;
    }

    @Override
    public List<Slider> findAll() {
        return sliderRepository.findAll();
    }

    @Override
    public Optional<Slider> findById(Long id) {
        return sliderRepository.findById(id);
    }

    @Override
    public Optional<Slider> save(Slider slider) {
        return Optional.of(sliderRepository.save(slider));
    }

    @Override
    public Optional<Slider> update(Long id, Slider slider) {
        Optional<Slider> persistedSlider = sliderRepository.findById(id);

        if (!persistedSlider.isPresent()) {
            throw new RuntimeException("Persisted slider ID " + id + " is not existed.");
        }

        persistedSlider.ifPresent(s -> {
            s.setTitle(slider.getTitle());
            s.setImage(slider.getImage());
            s.setDescription(slider.getDescription());
            s.setSubtitle(slider.getSubtitle());
            s.setJustifyCenter(slider.isJustifyCenter());
            s.setOrderButton(slider.isOrderButton());
            s.setViewButton(slider.isViewButton());
            s.setBackgroundImage(slider.isBackgroundImage());
            s.setProductId(slider.getProductId());
            s.setStyles(slider.getStyles());
        });

        Slider updatedSlider = sliderRepository.save(persistedSlider.get());
        return Optional.of(updatedSlider);
    }

    @Override
    public void delete(Long id) throws NotFoundException {
        Optional<Slider> persistedSlider = sliderRepository.findById(id);
        if (!persistedSlider.isPresent()) {
            throw new NotFoundException("Slider ID + " + id + " not found");
        }

        sliderRepository.deleteById(id);
    }
}
