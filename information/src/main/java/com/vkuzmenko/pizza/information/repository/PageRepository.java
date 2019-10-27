package com.vkuzmenko.pizza.information.repository;

import com.vkuzmenko.pizza.information.domain.Page;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PageRepository extends CrudRepository<Page, Long> {
    List<Page> findAll();

    @Override
    Optional<Page> findById(Long id);
}
