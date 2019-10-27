package com.vkuzmenko.pizza.product.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vkuzmenko.pizza.product.helper.PageBuilder;
import com.vkuzmenko.pizza.product.domain.Category;
import com.vkuzmenko.pizza.product.domain.Product;
import com.vkuzmenko.pizza.product.resource.ProductResourceAssembler;
import com.vkuzmenko.pizza.product.service.ProductService;
import com.vkuzmenko.pizza.product.service.ProductServiceImpl;
import javassist.NotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.vkuzmenko.pizza.product.Seeds.generateProduct;
import static org.assertj.core.api.Assertions.fail;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(ProductApiController.class)
public class ProductApiControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @MockBean
    private ProductResourceAssembler productResourceAssembler;

    @Autowired
    private ObjectMapper mapper;

    @Before
    public void setUp() {
        productResourceAssembler = new ProductResourceAssembler();

        mockMvc = MockMvcBuilders
                .standaloneSetup(new ProductApiController(productService, productResourceAssembler))
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers((ViewResolver) (viewName, locale)
                        -> new MappingJackson2JsonView())
                .build();
    }

    private final String productsTestApiUrl = "http://localhost/api/products";

    @Test
    public void whenFindAll_thenReturnProductList() throws Exception {
        Product product1 = generateProduct(1, "Product 1");
        Product product2 = generateProduct(2, "Product 2");

        List<Product> products = Arrays.asList(product1, product2);
        Page<Product> productPages = new PageBuilder<Product>()
                .elements(products)
                .pageRequest(PageRequest.of(0, 10))
                .totalElements(products.size())
                .build();

        given(productService.findAll(any(Pageable.class)))
                .willReturn(productPages);

        ResultActions result = mockMvc
                .perform(get(productsTestApiUrl).accept(MediaTypes.HAL_JSON_UTF8_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_UTF8_VALUE))
                .andExpect(jsonPath("links[0].href", is(productsTestApiUrl)))
                .andExpect(jsonPath("page.size", is(10)))
                .andExpect(jsonPath("page.totalElements", is(2)))
                .andExpect(jsonPath("page.totalPages", is(1)))
                .andExpect(jsonPath("page.number", is(0)));

        verifyMultipleJson(result, product1, product2);
    }

    @Test
    public void whenFindAllAndNoProductsFound_thenReturnStatusNotFound() throws Exception {
        given(productService.findAllByHot())
                .willReturn(Collections.emptyList());

        mockMvc
                .perform(get(productsTestApiUrl + "/top"))
                .andExpect(status().isNotFound())
                .andExpect(content().string(""));
    }

    @Test
    public void whenFindById_thenReturnProductResource() throws Exception {
        Product product = generateProduct(1, "Product 1");

        given(productService.findById(product.getId()))
                .willReturn(Optional.of(product));

        ResultActions result = mockMvc
                .perform(get(productsTestApiUrl + "/" + product.getId())
                        .accept(MediaTypes.HAL_JSON_UTF8_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_UTF8_VALUE));

        verifySingleJson(result, product);
    }

    @Test
    public void whenFindByIdAndNoProductFound_thenReturnStatusNotFound() throws Exception {
        final long testProductId = 1;

        given(productService.findById(testProductId))
                .willReturn(Optional.empty());

        mockMvc
                .perform(get(productsTestApiUrl + "/" + testProductId))
                .andExpect(status().isNotFound())
                .andExpect(content().string(""));
    }

    @Test
    public void whenTopProducts_thenReturnProductResourceList() throws Exception {
        Product product1 = generateProduct(1, "Product 1");
        Product product2 = generateProduct(2, "Product 2");

        given(productService.findAllByHot())
                .willReturn(Arrays.asList(product1, product2));

        ResultActions result = mockMvc
                .perform(get(productsTestApiUrl + "/top")
                        .accept(MediaTypes.HAL_JSON_UTF8_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_UTF8_VALUE))
                .andExpect(jsonPath("links[0].href", is(productsTestApiUrl)))
                .andExpect(jsonPath("links[0].rel", is("products")));

        verifyMultipleJson(result, product1, product2);
    }

    @Test
    public void whenTopProductsAndNoProductsFound_thenReturnStatusNotFound() throws Exception {
        given(productService.findAllByHot())
                .willReturn(Collections.emptyList());

        mockMvc.perform(get(productsTestApiUrl + "/top"))
                .andExpect(status().isNotFound())
                .andExpect(content().string(""));
    }

    @Test
    public void givenProduct_whenIsInserted_thenReturnStatusCreated() throws Exception {
        Product product = generateProduct(1, "Product 1");

        given(productService.save(any(Product.class)))
                .willReturn(Optional.of(product));

        ResultActions result = mockMvc.perform(post(productsTestApiUrl)
                .content(mapper.writeValueAsBytes(product))
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isCreated());

        verifySingleJson(result, product);
    }

    @Test
    public void givenProduct_whenIsNotInserted_thenReturnStatusBadRequest() throws Exception {
        Product product = generateProduct(0, "");

        given(productService.save(any(Product.class)))
                .willReturn(Optional.of(product));

        mockMvc.perform(post(productsTestApiUrl)
                .content(mapper.writeValueAsBytes(product))
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void givenProduct_whenIsUpdated_thenReturnStatusOK() throws Exception {
        Product updatedProduct = generateProduct(1, "Updated product name");

        given(productService.update(anyLong(), any(Product.class)))
                .willReturn(Optional.of(updatedProduct));

        ResultActions result = mockMvc
                .perform(put(productsTestApiUrl + "/" + updatedProduct.getId())
                        .content(mapper.writeValueAsBytes(updatedProduct))
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        verifySingleJson(result, updatedProduct);
    }

    @Test
    public void givenProduct_whenIsNotUpdated_thenReturnStatusBadRequest() throws Exception {
        Product updatedProduct = generateProduct(0, "");

        given(productService.update(anyLong(), any(Product.class)))
                .willReturn(Optional.of(updatedProduct));

        mockMvc.perform(put(productsTestApiUrl + "/" + updatedProduct.getId())
                .content(mapper.writeValueAsBytes(updatedProduct))
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void whenProductIsDeleted_thenReturn_StatusNoContent() throws Exception {
        Product product = generateProduct(1, "Product name");

        given(productService.findById(anyLong()))
                .willReturn(Optional.of(product));

        mockMvc
                .perform(delete(productsTestApiUrl + "/" + product.getId()))
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));
    }

    @Test(expected = NotFoundException.class)
    public void whenProductDeleteButNotFound_thenThrowException() throws Exception {
        final long deleteProductId = 1;

        doThrow(NotFoundException.class)
                .when(productService)
                .delete(anyLong());

        productService.delete(deleteProductId);

        mockMvc.perform(delete(productsTestApiUrl + "/" + deleteProductId))
                .andExpect(status().isBadRequest());
    }

    private void verifySingleJson(final ResultActions action, final Product product) throws Exception {
        action
                .andExpect(jsonPath("productId", is((int) product.getId())))
                .andExpect(jsonPath("name", is(product.getName())))
                .andExpect(jsonPath("description", is(product.getDescription())))
                .andExpect(jsonPath("shortDescription", is(product.getShortDescription())))
                .andExpect(jsonPath("price", is(product.getPrice())))
                .andExpect(jsonPath("image", is(product.getImage())))
                .andExpect(jsonPath("hot", is(product.getHot())))
                .andExpect(jsonPath("createdAt", is(product.getCreatedAt())))

                .andExpect(jsonPath("category.categoryId", is((int) product.getCategory().getCategoryId())))
                .andExpect(jsonPath("category.name", is(product.getCategory().getName())))
                .andExpect(jsonPath("category.description", is(product.getCategory().getDescription())))
                .andExpect(jsonPath("category.createdAt", is(product.getCategory().getCreatedAt())))

                .andExpect(jsonPath("links[0].href", is(productsTestApiUrl + "/" + product.getId())))
                .andExpect(jsonPath("links[0].rel", is("self")))
                .andExpect(jsonPath("links[1].href", is(productsTestApiUrl)))
                .andExpect(jsonPath("links[1].rel", is("products")));
    }

    private void verifyMultipleJson(final ResultActions action, final Product... products) throws Exception {
        for (int i = 0; i < products.length; i++) {
            action
                    .andExpect(jsonPath("content", hasSize(products.length)))
                    .andExpect(jsonPath("content[" + i + "].productId", is((int) products[i].getId())))
                    .andExpect(jsonPath("content[" + i + "].name", is(products[i].getName())))
                    .andExpect(jsonPath("content[" + i + "].description", is(products[i].getDescription())))
                    .andExpect(jsonPath("content[" + i + "].shortDescription", is(products[i].getShortDescription())))
                    .andExpect(jsonPath("content[" + i + "].price", is(products[i].getPrice())))
                    .andExpect(jsonPath("content[" + i + "].image", is(products[i].getImage())))
                    .andExpect(jsonPath("content[" + i + "].hot", is(products[i].getHot())))
                    .andExpect(jsonPath("content[" + i + "].createdAt", is(products[i].getCreatedAt())))
                    .andExpect(jsonPath("content[" + i + "].category.categoryId", is((int) products[i].getCategory().getCategoryId())))
                    .andExpect(jsonPath("content[" + i + "].category.name", is(products[i].getCategory().getName())))
                    .andExpect(jsonPath("content[" + i + "].category.description", is(products[i].getCategory().getDescription())))
                    .andExpect(jsonPath("content[" + i + "].category.createdAt", is(products[i].getCategory().getCreatedAt())))
                    .andExpect(jsonPath("content[" + i + "].links[0].href", is(productsTestApiUrl + "/" + products[i].getId())))
                    .andExpect(jsonPath("content[" + i + "].links[0].rel", is("self")));
        }
    }

}