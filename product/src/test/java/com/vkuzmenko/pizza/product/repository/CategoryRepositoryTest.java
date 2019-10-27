package com.vkuzmenko.pizza.product.repository;

import com.vkuzmenko.pizza.product.Application;
import com.vkuzmenko.pizza.product.H2JpaConfig;
import com.vkuzmenko.pizza.product.domain.Category;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.vkuzmenko.pizza.product.Seeds.generateCategory;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class, H2JpaConfig.class})
@Transactional
public class CategoryRepositoryTest {

    @Autowired
    CategoryRepository categoryRepository;

    @Before
    public void setUp() {
        categoryRepository.deleteAll();
    }

    @Test
    public void whenFindAll_thenReturnCategoryList() {
        Category category1 = generateCategory(0, "Category 1", 2);
        Category category2 = generateCategory(0, "Category 2", 2);

        categoryRepository.save(category1);
        categoryRepository.save(category2);

        List<Category> categoryList = categoryRepository.findAll();

        assertEquals(categoryList.size(), 2);
        assertEquals(categoryList.get(0).getName(), category1.getName());
        assertEquals(categoryList.get(0).getDescription(), category1.getDescription());
        assertEquals(categoryList.get(0).getCreatedAt(), category1.getCreatedAt());
        assertEquals(categoryList.get(0).getProducts().size(), 2);

        assertEquals(categoryList.get(1).getName(), category2.getName());
        assertEquals(categoryList.get(1).getDescription(), category2.getDescription());
        assertEquals(categoryList.get(1).getCreatedAt(), category2.getCreatedAt());
        assertEquals(categoryList.get(1).getProducts().size(), 2);
    }

    @Test
    public void whenFindById_thenReturnCategory() {
        Category category = generateCategory(0, "Category 1", 2);
        Category savedCategory = categoryRepository.save(category);

        Optional<Category> persistedCategory
                = categoryRepository.findById(savedCategory.getCategoryId());

        assertEquals(persistedCategory.get().getName(), category.getName());
        assertEquals(persistedCategory.get().getDescription(), category.getDescription());
        assertEquals(persistedCategory.get().getCreatedAt(), category.getCreatedAt());
        assertEquals(persistedCategory.get().getProducts().size(), 2);
    }

    @Test
    public void givenCategory_whenSaveOrUpdateAndRetrieve_thenOk() {
        Category category = generateCategory(0, "Category 1", 1);

        Category savedCategory = categoryRepository.save(category);

        assertEquals(savedCategory.getName(), category.getName());
        assertEquals(savedCategory.getDescription(), category.getDescription());
        assertEquals(savedCategory.getCreatedAt(), category.getCreatedAt());
        assertEquals(savedCategory.getProducts().size(), 1);
    }

    @Test
    public void givenCategory_whenDelete_thenOk() {
        Category category = generateCategory(0, "Category 1", 1);

        Category savedCategory = categoryRepository.save(category);
        categoryRepository.deleteById(category.getCategoryId());
        Optional<Category> persistedSlider = categoryRepository.findById(savedCategory.getCategoryId());

        assertFalse(persistedSlider.isPresent());
    }
}
