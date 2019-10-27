package com.vkuzmenko.pizza.common.repository;

import com.vkuzmenko.pizza.common.Application;
import com.vkuzmenko.pizza.common.H2JpaConfig;
import com.vkuzmenko.pizza.common.domain.Slider;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static com.vkuzmenko.pizza.common.Seeds.generateSlider;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class, H2JpaConfig.class})
@ActiveProfiles("test")
public class SliderRepositoryTest {

    @Autowired
    SliderRepository sliderRepository;

    @Before
    public void setUp() {
        sliderRepository.deleteAll();
    }

    @Test
    public void whenFindAll_thenReturnSliderList() {
        Slider slider1 = generateSlider(0, "Slider name 1");
        Slider slider2 = generateSlider(0, "Slider name 2");

        sliderRepository.save(slider1);
        sliderRepository.save(slider2);

        List<Slider> sliderList = sliderRepository.findAll();

        assertEquals(sliderList.size(), 2);
        assertEquals(sliderList.get(0).getTitle(), slider1.getTitle());
        assertEquals(sliderList.get(0).getDescription(), slider1.getDescription());
        assertEquals(sliderList.get(0).getImage(), slider1.getImage());
        assertEquals(sliderList.get(0).getSubtitle(), slider1.getSubtitle());

        assertEquals(sliderList.get(1).getTitle(), slider2.getTitle());
        assertEquals(sliderList.get(1).getDescription(), slider2.getDescription());
        assertEquals(sliderList.get(1).getImage(), slider2.getImage());
        assertEquals(sliderList.get(1).getSubtitle(), slider2.getSubtitle());
    }

    @Test
    public void whenFindById_thenReturnSlider() {
        Slider slider = generateSlider(0, "Slider name 1");

        Slider savedSlider = sliderRepository.save(slider);

        assertEquals(savedSlider.getTitle(), slider.getTitle());
        assertEquals(savedSlider.getDescription(), slider.getDescription());
        assertEquals(savedSlider.getImage(), slider.getImage());
        assertEquals(savedSlider.getSubtitle(), slider.getSubtitle());
    }

    @Test
    public void givenSlider_whenSaveOrUpdateAndRetrieve_thenOk() {
        Slider slider = generateSlider(0, "Slider name");

        Slider savedSlider = sliderRepository.save(slider);

        assertEquals(savedSlider.getTitle(), savedSlider.getTitle());
        assertEquals(savedSlider.getDescription(), savedSlider.getDescription());
        assertEquals(savedSlider.getImage(), savedSlider.getImage());
        assertEquals(savedSlider.getSubtitle(), savedSlider.getSubtitle());
    }


    @Test
    public void givenSlider_whenDelete_thenOk() {
        Slider slider = generateSlider(0, "Slider name");

        Slider savedSlider = sliderRepository.save(slider);
        sliderRepository.deleteById(slider.getId());
        Optional<Slider> persistedSlider = sliderRepository.findById(savedSlider.getId());

        assertFalse(persistedSlider.isPresent());
    }
}
