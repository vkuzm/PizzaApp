package com.vkuzmenko.pizza.product.service;

import com.vkuzmenko.pizza.product.domain.Category;
import com.vkuzmenko.pizza.product.repository.CategoryRepository;
import com.vkuzmenko.pizza.product.repository.ProductRepository;
import javassist.NotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.vkuzmenko.pizza.product.Seeds.generateCategory;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class CategoryServiceImplTest {

    private CategoryService categoryService;

    @MockBean
    private ProductRepository productRepository;

    @MockBean
    private CategoryRepository categoryRepository;

    @Before
    public void setUp() {
        categoryService = new CategoryServiceImpl(categoryRepository, productRepository);
    }

    @Test
    public void whenFindAll_thenReturnCategoryList() {
        Category category1 = generateCategory(1, "Category 1", 2);
        Category category2 = generateCategory(2, "Category 2", 2);

        when(categoryRepository.findAll())
                .thenReturn(Arrays.asList(category1, category2));

        List<Category> persistedCategories = categoryService.findAll();

        assertEquals(persistedCategories.size(), 2);
        verify(categoryRepository, times(1)).findAll();
    }

    @Test
    public void whenFindById_thenReturnCategory() {
        Category category = generateCategory(1, "Category name", 2);

        when(categoryRepository.findById(anyLong()))
                .thenReturn(Optional.of(category));

        Optional<Category> persistedCategory = categoryService.findById(anyLong());

        assertTrue(persistedCategory.isPresent());
        verify(categoryRepository, times(1)).findById(anyLong());
    }

    @Test
    public void getShowCase() {
        final int productLimit = 3;

        Category category1 = generateCategory(1, "Category 1", productLimit);
        Category category2 = generateCategory(2, "Category 2", productLimit);

        when(categoryRepository.findAllByOrderBySort())
                .thenReturn(Arrays.asList(category1, category2));

        List<Category> persistedCategories = categoryService.getShowCase(productLimit);

        assertEquals(persistedCategories.size(), 2);

        verify(categoryRepository, times(1))
                .findAllByOrderBySort();

        verify(productRepository, times(1))
                .findByCategoryId(category1.getCategoryId(), productLimit);

        verify(productRepository, times(1))
                .findByCategoryId(category2.getCategoryId(), productLimit);
    }

    @Test
    public void givenCategory_whenIsInserted_thenReturnCreatedCategory() {
        Category category = generateCategory(1, "Category name", 2);

        when(categoryRepository.save(any(Category.class)))
                .thenReturn(category);

        Optional<Category> savedCategory = categoryService.save(category);

        assertTrue(savedCategory.isPresent());
        assertEquals(savedCategory.get().getCategoryId(), category.getCategoryId());
        assertEquals(savedCategory.get().getName(), category.getName());
        assertEquals(savedCategory.get().getDescription(), category.getDescription());
        assertEquals(savedCategory.get().getProducts().size(), category.getProducts().size());

        verify(categoryRepository, times(1))
                .save(any(Category.class));
    }

    @Test
    public void givenCategory_whenIsUpdated_thenReturnUpdatedCategory() throws NotFoundException {
        Category category = generateCategory(1, "Updated category name", 2);

        given(categoryRepository.save(any(Category.class)))
                .willReturn(category);

        given(categoryRepository.findById(anyLong()))
                .willReturn(Optional.of(category));

        Optional<Category> updatedCategory = categoryService.update(category.getCategoryId(), category);

        assertTrue(updatedCategory.isPresent());
        assertEquals(updatedCategory.get().getCategoryId(), category.getCategoryId());
        assertEquals(updatedCategory.get().getName(), category.getName());
        assertEquals(updatedCategory.get().getDescription(), category.getDescription());
        assertEquals(updatedCategory.get().getProducts().size(), category.getProducts().size());

        verify(categoryRepository, times(1))
                .save(any(Category.class));

        verify(categoryRepository, times(1))
                .findById(anyLong());
    }

    @Test(expected = NotFoundException.class)
    public void givenCategory_whenUpdateNullCategory_thenThrowsException() throws NotFoundException {
        Category category = generateCategory(1, "Updated category name", 2);

        given(categoryRepository.findById(anyLong()))
                .willReturn(Optional.empty());

        categoryService.update(category.getCategoryId(), category);

        verify(categoryRepository, times(1)).save(any(Category.class));
        verify(categoryRepository, times(1)).findById(anyLong());
    }

    @Test
    public void whenCategoryIsDeletedSuccessfully_thenOK() throws NotFoundException {
        Category category = generateCategory(1, "Category name", 2);

        when(categoryRepository.findById(anyLong()))
                .thenReturn(Optional.of(category));

        categoryService.delete(category.getCategoryId());

        verify(categoryRepository, times(1))
                .findById(anyLong());

        verify(categoryRepository, times(1))
                .deleteById(anyLong());
    }

    @Test(expected = NotFoundException.class)
    public void whenDeleteAndCategoryIsNull_thenThrowsException() throws NotFoundException {
        given(categoryRepository.findById(anyLong()))
                .willReturn(Optional.empty());

        categoryService.delete(anyLong());

        verify(categoryRepository, times(1))
                .findById(anyLong());
    }

}