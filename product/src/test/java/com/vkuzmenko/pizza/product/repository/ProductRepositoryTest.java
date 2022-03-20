package com.vkuzmenko.pizza.product.repository;

import com.vkuzmenko.pizza.product.Application;
import com.vkuzmenko.pizza.product.H2JpaConfig;
import com.vkuzmenko.pizza.product.domain.Product;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.vkuzmenko.pizza.product.Seeds.generateProduct;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class, H2JpaConfig.class})
@Transactional
public class ProductRepositoryTest {

    @Autowired
    ProductRepository productRepository;

    @Before
    public void setUp() {
        productRepository.deleteAll();
    }

    @Test
    public void whenFindAll_thenReturnProductList() {
        Product product1 = generateProduct(0, "Product 1");
        Product product2 = generateProduct(0, "Product 2");

        productRepository.save(product1);
        productRepository.save(product2);

        Page<Product> productList = productRepository.findAll(PageRequest.of(0, 10));

        assertEquals(productList.getContent().size(), 2);
        assertEquals(productList.getContent().get(0).getName(), product1.getName());
        assertEquals(productList.getContent().get(0).getDescription(), product1.getDescription());
        assertEquals(productList.getContent().get(0).getCreatedAt(), product1.getCreatedAt());
        assertEquals(productList.getContent().get(0).getImage(), product1.getImage());
        assertEquals(productList.getContent().get(0).getCategoryId(), product1.getCategoryId());
        assertEquals(productList.getContent().get(0).getPrice(), product1.getPrice());

        assertEquals(productList.getContent().get(1).getName(), product2.getName());
        assertEquals(productList.getContent().get(1).getDescription(), product2.getDescription());
        assertEquals(productList.getContent().get(1).getCreatedAt(), product2.getCreatedAt());
        assertEquals(productList.getContent().get(1).getImage(), product2.getImage());
        assertEquals(productList.getContent().get(1).getCategoryId(), product1.getCategoryId());
        assertEquals(productList.getContent().get(1).getPrice(), product2.getPrice());
    }

    @Test
    public void whenFindById_thenReturnProduct() {
        Product product = generateProduct(0, "Product 1");
        Product savedProduct = productRepository.save(product);

        Optional<Product> persistedProduct
                = productRepository.findById(savedProduct.getId());

        assertEquals(persistedProduct.get().getName(), product.getName());
        assertEquals(persistedProduct.get().getDescription(), product.getDescription());
        assertEquals(persistedProduct.get().getCreatedAt(), product.getCreatedAt());
        assertEquals(persistedProduct.get().getImage(), product.getImage());
        assertEquals(persistedProduct.get().getCategoryId(), product.getCategoryId());
        assertEquals(persistedProduct.get().getPrice(), product.getPrice());
    }

    @Test
    public void givenProduct_whenSaveOrUpdateAndRetrieve_thenOk() {
        Product product = generateProduct(0, "Product 2");

        Product savedProduct = productRepository.save(product);

        assertEquals(savedProduct.getName(), product.getName());
        assertEquals(savedProduct.getDescription(), product.getDescription());
        assertEquals(savedProduct.getCreatedAt(), product.getCreatedAt());
        assertEquals(savedProduct.getImage(), product.getImage());
        assertEquals(savedProduct.getCategoryId(), product.getCategoryId());
        assertEquals(savedProduct.getPrice(), product.getPrice());
    }

    @Test
    public void givenProduct_whenDelete_thenOk() {
        Product product = generateProduct(0, "Product 2");
        Product savedProduct = productRepository.save(product);

        productRepository.deleteById(savedProduct.getId());
        Optional<Product> persistedProduct = productRepository.findById(savedProduct.getId());

        assertFalse(persistedProduct.isPresent());
    }
}
