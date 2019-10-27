package com.vkuzmenko.pizza.product.service;

import com.vkuzmenko.pizza.product.domain.Category;
import com.vkuzmenko.pizza.product.domain.Product;
import com.vkuzmenko.pizza.product.repository.ProductRepository;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepo;
    private final CategoryService categoryService;

    public ProductServiceImpl(ProductRepository productRepo, CategoryService categoryService) {
        this.productRepo = productRepo;
        this.categoryService = categoryService;
    }

    @Override
    public Page<Product> findAll(Pageable pageable) {
        return productRepo.findAll(pageable);
    }

    @Override
    public Optional<Product> findById(Long postId) {
        return productRepo.findById(postId);
    }

    @Override
    public Optional<Product> save(Product product) {
        Optional<Category> category =
                categoryService.findById(product.getCategoryId());
        category.ifPresent(product::setCategory);

        return Optional.of(productRepo.save(product));
    }

    @Override
    public Optional<Product> update(Long id, Product product) throws NotFoundException {
        Optional<Product> persistedProduct = productRepo.findById(id);

        if (!persistedProduct.isPresent()) {
            throw new NotFoundException("Persisted product ID " + id + " is not existed.");
        }

        Optional<Category> category = categoryService.findById(product.getCategoryId());

        persistedProduct.ifPresent(p -> {
            p.setName(product.getName());
            p.setImage(product.getImage());
            p.setShortDescription(product.getShortDescription());
            p.setCreatedAt(product.getCreatedAt());
            p.setPrice(product.getPrice());
            p.setDescription(product.getDescription());
            p.setHot(product.getHot());
            p.setCategory(category.orElse(new Category()));
        });

        Product updatedProduct = productRepo.save(persistedProduct.get());
        return Optional.of(updatedProduct);
    }

    @Override
    public void delete(final Long id) throws NotFoundException {
        Optional<Product> persistedProduct = productRepo.findById(id);
        if (!persistedProduct.isPresent()) {
            throw new NotFoundException("Product ID + " + id + " not found");
        }

        productRepo.deleteById(id);
    }

    @Override
    public List<Product> findAllByHot() {
        return productRepo.findAllByHotIsTrue();
    }
}
