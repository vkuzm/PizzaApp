package com.vkuzmenko.pizza.common.repository;

import com.vkuzmenko.pizza.common.domain.CustomField;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomFieldRepository extends CrudRepository<CustomField, Long> {
    List<CustomField> findAll();
}
