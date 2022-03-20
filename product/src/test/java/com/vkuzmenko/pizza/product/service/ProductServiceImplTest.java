package com.vkuzmenko.pizza.product.service;

import com.vkuzmenko.pizza.product.domain.Category;
import com.vkuzmenko.pizza.product.domain.Product;
import com.vkuzmenko.pizza.product.helper.PageBuilder;
import com.vkuzmenko.pizza.product.repository.ProductRepository;
import javassist.NotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.vkuzmenko.pizza.product.Seeds.generateCategory;
import static com.vkuzmenko.pizza.product.Seeds.generateProduct;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class ProductServiceImplTest {

    private ProductService productService;

    @MockBean
    private ProductRepository productRepository;

    @MockBean
    private CategoryService categoryService;

    @Before
    public void setUp() {
        productService = new ProductServiceImpl(productRepository, categoryService);
    }

    @Test
    public void whenFindAll_thenReturnProductList() {
        PageRequest pageRequest = PageRequest.of(0, 10);

        Product product1 = generateProduct(1, "Product 1");
        Product product2 = generateProduct(2, "Product 2");

        List<Product> products = Arrays.asList(product1, product2);
        Page<Product> productPages = new PageBuilder<Product>()
                .elements(products)
                .pageRequest(pageRequest)
                .totalElements(products.size())
                .build();

        when(productRepository.findAll(any(Pageable.class)))
                .thenReturn(productPages);

        Page<Product> persistedProducts =
                productService.findAll(pageRequest);

        assertEquals(persistedProducts.getContent().get(0).getId(), product1.getId());
        assertEquals(persistedProducts.getContent().get(0).getName(), product1.getName());
        assertEquals(persistedProducts.getContent().get(1).getId(), product2.getId());
        assertEquals(persistedProducts.getContent().get(1).getName(), product2.getName());
        assertEquals(persistedProducts.getSize(), 10);
        assertEquals(persistedProducts.getTotalPages(), 1);
        assertEquals(persistedProducts.getNumber(), 0);

        verify(productRepository, times(1)).findAll(any(Pageable.class));
    }

    @Test
    public void whenFindById_thenReturnProduct() {
        Product product = generateProduct(1, "Product 1");

        given(productRepository.findById(product.getId()))
                .willReturn(Optional.of(product));

        Optional<Product> persistedProduct = productService.findById(product.getId());

        assertTrue(persistedProduct.isPresent());
        assertEquals(persistedProduct.get().getId(), product.getId());
        assertEquals(persistedProduct.get().getName(), product.getName());

        verify(productRepository, times(1)).findById(anyLong());
    }

    @Test
    public void givenProduct_whenIsInserted_thenReturnCreatedProduct() {
        Product product = generateProduct(1, "Product 1");
        Category category = generateCategory(1, "Category name", 0);

        given(productRepository.save(any(Product.class)))
                .willReturn(product);

        given(categoryService.findById(anyLong()))
                .willReturn(Optional.of(category));

        Optional<Product> savedProduct = productService.save(product);

        assertTrue(savedProduct.isPresent());
        assertEquals(savedProduct.get().getId(), product.getId());
        assertEquals(savedProduct.get().getName(), product.getName());
        assertEquals(savedProduct.get().getCategory().getCategoryId(), category.getCategoryId());
        assertEquals(savedProduct.get().getCategory().getName(), category.getName());

        verify(productRepository, times(1)).save(any(Product.class));
        verify(categoryService, times(1)).findById(anyLong());
    }

    @Test
    public void givenProduct_whenIsUpdated_thenReturnUpdatedProduct() throws NotFoundException {
        Product product = generateProduct(1, "Updated product name");

        given(productRepository.save(any(Product.class)))
                .willReturn(product);

        given(productRepository.findById(anyLong()))
                .willReturn(Optional.of(product));

        Optional<Product> updatedProduct = productService.update(product.getId(), product);

        assertTrue(updatedProduct.isPresent());
        assertEquals(updatedProduct.get().getId(), product.getId());
        assertEquals(updatedProduct.get().getName(), product.getName());
        assertEquals(updatedProduct.get().getDescription(), product.getDescription());

        verify(productRepository, times(1))
                .save(any(Product.class));

        verify(productRepository, times(1))
                .findById(anyLong());
    }

    @Test(expected = NotFoundException.class)
    public void givenProduct_whenUpdateNullProduct_thenThrowsException() throws NotFoundException {
        Product product = generateProduct(1, "Updated product name");

        given(productRepository.findById(anyLong()))
                .willReturn(Optional.empty());

        productService.update(product.getId(), product);

        verify(productRepository, times(1)).save(any(Product.class));
        verify(productRepository, times(1)).findById(anyLong());
    }

    @Test
    public void whenFindAllByHot_thenReturnHotProductList() {
        Product product1 = generateProduct(1, "Product 1");
        Product product2 = generateProduct(2, "Product 2");

        when(productRepository.findAllByHotIsTrue())
                .thenReturn(Arrays.asList(product1, product2));

        List<Product> persistedProducts = productService.findAllByHot();

        assertEquals(persistedProducts.get(0).getId(), product1.getId());
        assertEquals(persistedProducts.get(0).getName(), product1.getName());
        assertEquals(persistedProducts.get(1).getId(), product2.getId());
        assertEquals(persistedProducts.get(1).getName(), product2.getName());
        assertEquals(persistedProducts.size(), 2);

        verify(productRepository, times(1)).findAllByHotIsTrue();
    }

    @Test
    public void whenProductIsDeletedSuccessfully_thenOK() throws NotFoundException {
        Product product = generateProduct(1, "Product name");

        given(productRepository.findById(anyLong()))
                .willReturn(Optional.of(product));

        productService.delete(product.getId());

        verify(productRepository, times(1))
                .findById(anyLong());

        verify(productRepository, times(1))
                .deleteById(anyLong());
    }

    @Test(expected = NotFoundException.class)
    public void whenProductIsNull_thenThrowsException() throws NotFoundException {
        given(productRepository.findById(anyLong()))
                .willReturn(Optional.empty());

        productService.delete(anyLong());

        verify(productRepository, times(1))
                .findById(anyLong());
    }

}