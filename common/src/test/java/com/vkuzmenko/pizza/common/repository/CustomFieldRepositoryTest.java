package com.vkuzmenko.pizza.common.repository;

import com.vkuzmenko.pizza.common.Application;
import com.vkuzmenko.pizza.common.H2JpaConfig;
import com.vkuzmenko.pizza.common.domain.CustomField;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static com.vkuzmenko.pizza.common.Seeds.generateCustomField;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class, H2JpaConfig.class})
@ActiveProfiles("test")
public class CustomFieldRepositoryTest {

    @Autowired
    CustomFieldRepository customFieldRepository;

    @Test
    public void whenFindAll_thenReturnCustomFieldList() {
        CustomField customField = generateCustomField(1, "Custom field 1");
        CustomField customField2 = generateCustomField(2, "Custom field 2");

        customFieldRepository.save(customField);
        customFieldRepository.save(customField2);

        List<CustomField> customFieldList = customFieldRepository.findAll();

        assertEquals(2, customFieldList.size());
        assertEquals(customFieldList.get(0).getId(), customField.getId());
        assertEquals(customFieldList.get(0).getFieldName(), customField.getFieldName());
        assertEquals(customFieldList.get(0).getContent(), customField.getContent());
        assertEquals(customFieldList.get(0).getSort(), customField.getSort());

        assertEquals(customFieldList.get(1).getId(), customField2.getId());
        assertEquals(customFieldList.get(1).getFieldName(), customField2.getFieldName());
        assertEquals(customFieldList.get(1).getContent(), customField2.getContent());
        assertEquals(customFieldList.get(1).getSort(), customField2.getSort());
    }

    @Test
    public void givenCustomField_whenSaveAndRetrieve_thenOk() {
        CustomField customField = generateCustomField(1, "Custom field 1");

        CustomField savedCustomField = customFieldRepository.save(customField);
        Optional<CustomField> persistedCustomField =
                customFieldRepository.findById(savedCustomField.getId());

        assertEquals(persistedCustomField.get().getId(), customField.getId());
        assertEquals(persistedCustomField.get().getFieldName(), customField.getFieldName());
        assertEquals(persistedCustomField.get().getContent(), customField.getContent());
        assertEquals(persistedCustomField.get().getSort(), customField.getSort());
    }

}
