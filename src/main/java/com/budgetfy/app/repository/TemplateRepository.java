package com.budgetfy.app.repository;

import com.budgetfy.app.model.Template;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TemplateRepository extends JpaRepository<Template, Integer> {
    void deleteAllByUserId(Integer userId);
    void deleteAllByAccountId(Integer userId);
    List<Template> findTemplatesByUserId(Integer userId);
    boolean existsByNameAndUserId(String name, Integer userId);
    List<Template> searchByNameContainingIgnoreCaseAndUserId(String templateName, Integer userId);

}
