package com.vkuzmenko.pizza.product.service;

import com.vkuzmenko.pizza.product.domain.Category;
import com.vkuzmenko.pizza.product.domain.Product;
import com.vkuzmenko.pizza.product.repository.CategoryRepository;
import com.vkuzmenko.pizza.product.repository.ProductRepository;
import javassist.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepo;
    private final ProductRepository productRepo;

    public CategoryServiceImpl(CategoryRepository categoryRepo, ProductRepository productRepo) {
        this.categoryRepo = categoryRepo;
        this.productRepo = productRepo;
    }

    @Override
    public List<Category> findAll() {
        return categoryRepo.findAll();
    }

    @Override
    public Optional<Category> findById(Long id) {
        return categoryRepo.findById(id);
    }

    @Override
    public List<Category> getShowCase(Integer productLimit) {
        List<Category> categories = categoryRepo.findAllByOrderBySort();

        categories.forEach(category -> {
            List<Product> products = productRepo
                    .findByCategoryId(category.getCategoryId(), productLimit);

            category.setProducts(products);
        });

        return categories;
    }

    @Override
    public Optional<Category> save(Category category) {
        Category savedCategory = categoryRepo.save(category);
        return Optional.of(savedCategory);
    }

    @Override
    public Optional<Category> update(Long id, Category category) throws NotFoundException {
        Optional<Category> persistedCategory = categoryRepo.findById(id);

        if (!persistedCategory.isPresent()) {
            throw new NotFoundException("Persisted category ID " + id + " is not existed.");
        }

        persistedCategory.ifPresent(p -> {
            p.setDescription(category.getDescription());
            p.setName(category.getName());
            p.setCreatedAt(category.getCreatedAt());
        });

        Category savedCategory = categoryRepo.save(persistedCategory.get());
        return Optional.of(savedCategory);
    }

    @Override
    public void delete(Long id) throws NotFoundException {
        Optional<Category> persistedCategory = categoryRepo.findById(id);
        if (!persistedCategory.isPresent()) {
            throw new NotFoundException("Category ID + " + id + " not found");
        }

        categoryRepo.deleteById(id);
    }
}
