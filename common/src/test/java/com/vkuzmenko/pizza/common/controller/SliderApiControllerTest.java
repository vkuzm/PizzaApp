package com.vkuzmenko.pizza.common.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vkuzmenko.pizza.common.domain.Slider;
import com.vkuzmenko.pizza.common.service.SliderService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static com.vkuzmenko.pizza.common.Seeds.generateSlider;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(SliderApiController.class)
public class SliderApiControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private SliderService sliderService;


    @Autowired
    private ObjectMapper mapper;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(new SliderApiController(sliderService))
                .build();
    }

    private final String sliderTestApiUrl = "http://localhost/api/slider";

    @Test
    public void whenFindAll_thenReturnSliderList() throws Exception {
        Slider slider1 = generateSlider(1, "Slider 1");
        Slider slider2 = generateSlider(2, "Slider 2");

        given(sliderService.findAll())
                .willReturn(Arrays.asList(slider1, slider2));

        ResultActions result = mockMvc
                .perform(get(sliderTestApiUrl).accept(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE));

        verifyMultipleJson(result, slider1, slider2);
    }

    @Test
    public void whenFindAllAndNoSlidesFound_thenReturnStatusNotFound() throws Exception {
        given(sliderService.findAll())
                .willReturn(Collections.emptyList());

        mockMvc
                .perform(get(sliderTestApiUrl))
                .andExpect(status().isNotFound())
                .andExpect(content().string(""));
    }

    @Test
    public void whenFindById_thenReturnSlider() throws Exception {
        Slider slider = generateSlider(1, "Slider 1");

        given(sliderService.findById(anyLong()))
                .willReturn(Optional.of(slider));

        ResultActions result = mockMvc
                .perform(get(sliderTestApiUrl + "/" + slider.getId())
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE));

        verifySingleJson(result, slider);
    }

    @Test
    public void whenFindByIdAndNoSliderFound_thenReturnStatusNotFound() throws Exception {
        given(sliderService.findById(anyLong()))
                .willReturn(Optional.empty());

        mockMvc
                .perform(get(sliderTestApiUrl + "/" + anyLong()))
                .andExpect(status().isNotFound())
                .andExpect(content().string(""));
    }

    @Test
    public void givenSlider_whenIsInserted_thenReturnStatusCreated() throws Exception {
        Slider slider = generateSlider(1, "Slider 1");

        given(sliderService.save(any(Slider.class)))
                .willReturn(Optional.of(slider));

        ResultActions result = mockMvc.perform(post(sliderTestApiUrl)
                .content(mapper.writeValueAsBytes(slider))
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isCreated());

        verifySingleJson(result, slider);
    }

    @Test
    public void givenSlider_whenIsNotInserted_thenReturnStatusBadRequest() throws Exception {
        Slider slider = generateSlider(0, "");

        given(sliderService.save(any(Slider.class)))
                .willReturn(Optional.of(slider));

        mockMvc.perform(post(sliderTestApiUrl)
                .content(mapper.writeValueAsBytes(slider))
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void givenSlider_whenIsUpdated_thenReturnStatusOK() throws Exception {
        Slider updatedSlider = generateSlider(1, "Updated slider name");

        given(sliderService.update(anyLong(), any(Slider.class)))
                .willReturn(Optional.of(updatedSlider));

        ResultActions result = mockMvc
                .perform(put(sliderTestApiUrl + "/" + updatedSlider.getId())
                        .content(mapper.writeValueAsBytes(updatedSlider))
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        verifySingleJson(result, updatedSlider);
    }

    @Test
    public void givenSlider_whenIsNotUpdated_thenReturnStatusBadRequest() throws Exception {
        Slider updatedSlider = generateSlider(0, "");

        given(sliderService.update(anyLong(), any(Slider.class)))
                .willReturn(Optional.of(updatedSlider));

        mockMvc.perform(put(sliderTestApiUrl + "/" + updatedSlider.getId())
                .content(mapper.writeValueAsBytes(updatedSlider))
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void whenSliderIsDeleted_thenReturn_StatusNoContent() throws Exception {
        Slider slider = generateSlider(1, "Slider name");

        given(sliderService.findById(anyLong()))
                .willReturn(Optional.of(slider));

        mockMvc
                .perform(delete(sliderTestApiUrl + "/" + slider.getId()))
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));
    }

    private void verifySingleJson(final ResultActions action, final Slider slider) throws Exception {
        action
                .andExpect(jsonPath("id", is((int) slider.getId())))
                .andExpect(jsonPath("title", is(slider.getTitle())))
                .andExpect(jsonPath("subtitle", is(slider.getSubtitle())))
                .andExpect(jsonPath("description", is(slider.getDescription())))
                .andExpect(jsonPath("image", is(slider.getImage())))
                .andExpect(jsonPath("productId", is((int) slider.getProductId())))
                .andExpect(jsonPath("styles", is(slider.getStyles())));
    }

    private void verifyMultipleJson(final ResultActions action, final Slider... sliders) throws Exception {
        for (int i = 0; i < sliders.length; i++) {
            action
                    .andExpect(jsonPath("$", hasSize(sliders.length)))
                    .andExpect(jsonPath("$[" + i + "].id", is((int) sliders[i].getId())))
                    .andExpect(jsonPath("$[" + i + "].title", is(sliders[i].getTitle())))
                    .andExpect(jsonPath("$[" + i + "].subtitle", is(sliders[i].getSubtitle())))
                    .andExpect(jsonPath("$[" + i + "].description", is(sliders[i].getDescription())))
                    .andExpect(jsonPath("$[" + i + "].image", is(sliders[i].getImage())))
                    .andExpect(jsonPath("$[" + i + "].productId", is((int) sliders[i].getProductId())))
                    .andExpect(jsonPath("$[" + i + "].styles", is(sliders[i].getStyles())));
        }
    }

}