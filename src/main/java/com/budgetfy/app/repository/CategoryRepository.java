package com.budgetfy.app.repository;

import com.budgetfy.app.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    boolean existsByName(String name);
    List<Category> findAllByShowTrue();
}
