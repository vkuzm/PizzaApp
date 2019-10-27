package com.vkuzmenko.pizza.common.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vkuzmenko.pizza.common.domain.CustomField;
import com.vkuzmenko.pizza.common.repository.CustomFieldRepository;
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

import static com.vkuzmenko.pizza.common.Seeds.generateCustomField;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(CustomFieldController.class)
public class CustomFieldControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private CustomFieldRepository customFieldRepository;

    @Autowired
    private ObjectMapper mapper;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(new CustomFieldController(customFieldRepository))
                .build();
    }

    private final String customFieldsTestApiUrl = "http://localhost/api/customFields";

    @Test
    public void whenGetFields_thenReturnCustomFieldsList() throws Exception {
        CustomField customField1 = generateCustomField(1, "FIeld name 1");
        CustomField customField2 = generateCustomField(2, "FIeld name 2");

        given(customFieldRepository.findAll())
                .willReturn(Arrays.asList(customField1, customField2));

        ResultActions result = mockMvc
                .perform(get(customFieldsTestApiUrl).accept(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE));

        verifyMultipleJson(result, customField1, customField2);
    }

    @Test
    public void givenCustomField_whenIsInserted_thenReturnStatusCreated() throws Exception {
        CustomField customField = generateCustomField(1, "FIeld name 1");

        given(customFieldRepository.save(any(CustomField.class)))
                .willReturn(customField);

        ResultActions result = mockMvc.perform(post(customFieldsTestApiUrl)
                .content(mapper.writeValueAsBytes(customField))
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isCreated());

        verifySingleJson(result, customField);
    }

    @Test
    public void givenCustomField_whenIsNotInserted_thenReturnStatusBadRequest() throws Exception {
        CustomField customField = generateCustomField(0, "");

        given(customFieldRepository.save(any(CustomField.class)))
                .willReturn(customField);

        mockMvc.perform(post(customFieldsTestApiUrl)
                .content(mapper.writeValueAsBytes(customField))
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest());
    }

    private void verifySingleJson(final ResultActions action, final CustomField customField) throws Exception {
        action
                .andExpect(jsonPath("id", is((int) customField.getId())))
                .andExpect(jsonPath("fieldName", is(customField.getFieldName())))
                .andExpect(jsonPath("content", is(customField.getContent())))
                .andExpect(jsonPath("sort", is(customField.getSort())));
    }

    private void verifyMultipleJson(final ResultActions action, final CustomField... customFields) throws Exception {
        for (int i = 0; i < customFields.length; i++) {
            action
                    .andExpect(jsonPath("$", hasSize(customFields.length)))
                    .andExpect(jsonPath("$[" + i + "].id", is((int) customFields[i].getId())))
                    .andExpect(jsonPath("$[" + i + "].fieldName", is(customFields[i].getFieldName())))
                    .andExpect(jsonPath("$[" + i + "].content", is(customFields[i].getContent())))
                    .andExpect(jsonPath("$[" + i + "].sort", is(customFields[i].getSort())));
        }
    }
}