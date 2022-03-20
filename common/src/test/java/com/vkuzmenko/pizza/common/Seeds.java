package com.vkuzmenko.pizza.common;

import com.vkuzmenko.pizza.common.domain.CustomField;
import com.vkuzmenko.pizza.common.domain.Slider;

public class Seeds {

    public static CustomField generateCustomField(long fieldId, String fieldName) {
        CustomField customField = new CustomField();
        customField.setId(fieldId);
        customField.setFieldName(fieldName);
        customField.setContent("Field content");
        customField.setSort(1);

        return customField;
    }

    public static Slider generateSlider(long sliderId, String sliderName) {
        Slider slider = new Slider();
        if (sliderId > 0) {
            slider.setId(sliderId);
        }
        slider.setProductId(10);
        slider.setTitle(sliderName);
        slider.setSubtitle("Slider subtitle");
        slider.setDescription("Slider description");
        slider.setImage("slider-image.jpg");
        slider.setStyles("text-align");

        return slider;
    }
}
