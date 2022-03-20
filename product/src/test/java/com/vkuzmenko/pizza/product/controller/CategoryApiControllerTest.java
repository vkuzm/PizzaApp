package com.vkuzmenko.pizza.product.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vkuzmenko.pizza.product.domain.Category;
import com.vkuzmenko.pizza.product.service.CategoryService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static com.vkuzmenko.pizza.product.Seeds.generateCategory;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(CategoryApiController.class)
public class CategoryApiControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private CategoryService categoryService;

    @Autowired
    private ObjectMapper mapper;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(new CategoryApiController(categoryService))
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers((ViewResolver) (viewName, locale)
                        -> new MappingJackson2JsonView())
                .build();
    }

    private final String categoriesTestApiUrl = "http://localhost/api/categories";

    @Test
    public void whenFindAll_thenReturnCategoryList() throws Exception {
        Category category1 = generateCategory(1, "Category 1", 2);
        Category category2 = generateCategory(2, "Category 2", 2);

        given(categoryService.findAll())
                .willReturn(Arrays.asList(category1, category2));

        ResultActions result = mockMvc
                .perform(get(categoriesTestApiUrl).accept(MediaTypes.HAL_JSON_UTF8_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_UTF8_VALUE))
                .andExpect(jsonPath("links[0].href", is(categoriesTestApiUrl)));

        verifyMultipleJson(result, category1, category2);
    }

    @Test
    public void whenFindAllAndNoCategoriesFound_thenReturnStatusNotFound() throws Exception {
        given(categoryService.findAll())
                .willReturn(Collections.emptyList());

        mockMvc
                .perform(get(categoriesTestApiUrl))
                .andExpect(status().isNotFound())
                .andExpect(content().string(""));
    }

    @Test
    public void whenFindById_thenReturnCategoryResource() throws Exception {
        Category category = generateCategory(1, "Category name 1", 2);

        given(categoryService.findById(category.getCategoryId()))
                .willReturn(Optional.of(category));

        ResultActions result = mockMvc
                .perform(get(categoriesTestApiUrl + "/" + category.getCategoryId())
                        .accept(MediaTypes.HAL_JSON_UTF8_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_UTF8_VALUE));

        verifySingleJson(result, category);
    }

    @Test
    public void whenFindByIdAndNoCategoryFound_thenReturnStatusNotFound() throws Exception {
        final long testCategoryId = 1;

        given(categoryService.findById(testCategoryId))
                .willReturn(Optional.empty());

        mockMvc
                .perform(get(categoriesTestApiUrl + "/" + testCategoryId))
                .andExpect(status().isNotFound())
                .andExpect(content().string(""));
    }

    @Test
    public void whenGetShowCase_thenReturnShowCaseItems() throws Exception {
        Category category1 = generateCategory(1, "Category 1", 2);
        Category category2 = generateCategory(2, "Category 2", 2);

        given(categoryService.getShowCase(any(Integer.class)))
                .willReturn(Arrays.asList(category1, category2));

        ResultActions result = mockMvc
                .perform(get(categoriesTestApiUrl + "/showcase").accept(MediaTypes.HAL_JSON_UTF8_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_UTF8_VALUE))
                .andExpect(jsonPath("links[0].href", is(categoriesTestApiUrl)));

        verifyMultipleJson(result, category1, category2);
    }

    @Test
    public void whenGetShowCaseAndNoItemsFound_thenReturnStatusNotFound() throws Exception {
        given(categoryService.getShowCase(any(Integer.class)))
                .willReturn(new ArrayList<>());

        mockMvc
                .perform(get(categoriesTestApiUrl + "/showcase"))
                .andExpect(status().isNotFound())
                .andExpect(content().string(""));
    }

    @Test
    public void givenCategory_whenIsInserted_thenReturnStatusCreated() throws Exception {
        Category category = generateCategory(1, "Category name", 2);

        given(categoryService.save(any(Category.class)))
                .willReturn(Optional.of(category));

        ResultActions result = mockMvc.perform(post(categoriesTestApiUrl)
                .content(mapper.writeValueAsBytes(category))
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isCreated());

        verifySingleJson(result, category);
    }

    @Test
    public void givenCategory_whenIsNotInserted_thenReturnStatusBadRequest() throws Exception {
        Category category = generateCategory(0, "", 2);

        given(categoryService.save(any(Category.class)))
                .willReturn(Optional.of(category));

        mockMvc.perform(post(categoriesTestApiUrl)
                .content(mapper.writeValueAsBytes(category))
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void givenCategory_whenIsUpdated_thenReturnStatusOK() throws Exception {
        Category updatedCategory = generateCategory(1, "Updated category name", 2);

        given(categoryService.update(anyLong(), any(Category.class)))
                .willReturn(Optional.of(updatedCategory));

        ResultActions result = mockMvc
                .perform(put(categoriesTestApiUrl + "/" + updatedCategory.getCategoryId())
                        .content(mapper.writeValueAsBytes(updatedCategory))
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        verifySingleJson(result, updatedCategory);
    }

    @Test
    public void givenCategory_whenIsNotUpdated_thenReturnStatusBadRequest() throws Exception {
        Category updatedCategory = generateCategory(0, "", 2);

        given(categoryService.update(anyLong(), any(Category.class)))
                .willReturn(Optional.of(updatedCategory));

        mockMvc.perform(put(categoriesTestApiUrl + "/" + updatedCategory.getCategoryId())
                .content(mapper.writeValueAsBytes(updatedCategory))
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest());
    }

/*    @Test(expected = NotFoundException.class)
    public void givenProduct_whenProductNotFound_thenThrowNotFoundException() throws Exception {
        Product updatedProduct = generateProduct(1, "Product name");

        given(productService.update(0L, any(Product.class)))
                .willReturn(Optional.of(updatedProduct));

        mockMvc.perform(put(productsTestApiUrl + "/" + updatedProduct.getId())
                .content(mapper.writeValueAsBytes(updatedProduct))
                .contentType(MediaType.APPLICATION_JSON_UTF8));
    }*/

    @Test
    public void whenCategoryIsDeleted_thenReturn_StatusNoContent() throws Exception {
        Category category = generateCategory(1, "Category name", 2);

        given(categoryService.findById(anyLong()))
                .willReturn(Optional.of(category));

        mockMvc
                .perform(delete(categoriesTestApiUrl + "/" + category.getCategoryId()))
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));
    }

    private void verifySingleJson(final ResultActions action, final Category category) throws Exception {
        action
                .andExpect(jsonPath("categoryId", is((int) category.getCategoryId())))
                .andExpect(jsonPath("name", is(category.getName())))
                .andExpect(jsonPath("description", is(category.getDescription())))
                .andExpect(jsonPath("createdAt", is(category.getCreatedAt())))

                .andExpect(jsonPath("links[0].href", is(categoriesTestApiUrl + "/" + category.getCategoryId())))
                .andExpect(jsonPath("links[0].rel", is("self")))
                .andExpect(jsonPath("links[1].href", is(categoriesTestApiUrl)))
                .andExpect(jsonPath("links[1].rel", is("categories")));

        for (int j = 0; j < category.getProducts().size(); j++) {
            action
                    .andExpect(jsonPath("products[" + j + "].productId", is((int) category.getProducts().get(j).getId())))
                    .andExpect(jsonPath("products[" + j + "].name", is(category.getProducts().get(j).getName())))
                    .andExpect(jsonPath("products[" + j + "].description", is(category.getProducts().get(j).getDescription())))
                    .andExpect(jsonPath("products[" + j + "].shortDescription", is(category.getProducts().get(j).getShortDescription())))
                    .andExpect(jsonPath("products[" + j + "].price", is(category.getProducts().get(j).getPrice())))
                    .andExpect(jsonPath("products[" + j + "].image", is(category.getProducts().get(j).getImage())))
                    .andExpect(jsonPath("products[" + j + "].hot", is(category.getProducts().get(j).getHot())));
        }
    }

    private void verifyMultipleJson(final ResultActions action, final Category... categories) throws Exception {
        for (int i = 0; i < categories.length; i++) {
            action
                    .andExpect(jsonPath("content", hasSize(categories.length)))
                    .andExpect(jsonPath("content[" + i + "].categoryId", is((int) categories[i].getCategoryId())))
                    .andExpect(jsonPath("content[" + i + "].name", is(categories[i].getName())))
                    .andExpect(jsonPath("content[" + i + "].description", is(categories[i].getDescription())))
                    .andExpect(jsonPath("content[" + i + "].createdAt", is(categories[i].getCreatedAt())))
                    .andExpect(jsonPath("content[" + i + "].links[0].href", is(categoriesTestApiUrl + "/" + categories[i].getCategoryId())))
                    .andExpect(jsonPath("content[" + i + "].links[0].rel", is("self")));

            for (int j = 0; j < categories[i].getProducts().size(); j++) {
                action
                        .andExpect(jsonPath("content[" + i + "].products[" + j + "].productId", is((int) categories[i].getProducts().get(j).getId())))
                        .andExpect(jsonPath("content[" + i + "].products[" + j + "].name", is(categories[i].getProducts().get(j).getName())))
                        .andExpect(jsonPath("content[" + i + "].products[" + j + "].description", is(categories[i].getProducts().get(j).getDescription())))
                        .andExpect(jsonPath("content[" + i + "].products[" + j + "].shortDescription", is(categories[i].getProducts().get(j).getShortDescription())))
                        .andExpect(jsonPath("content[" + i + "].products[" + j + "].price", is(categories[i].getProducts().get(j).getPrice())))
                        .andExpect(jsonPath("content[" + i + "].products[" + j + "].image", is(categories[i].getProducts().get(j).getImage())))
                        .andExpect(jsonPath("content[" + i + "].products[" + j + "].hot", is(categories[i].getProducts().get(j).getHot())));
            }
        }
    }

}