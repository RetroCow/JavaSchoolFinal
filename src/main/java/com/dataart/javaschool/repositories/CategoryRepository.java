package com.dataart.javaschool.repositories;

import com.dataart.javaschool.models.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Long> {
}
