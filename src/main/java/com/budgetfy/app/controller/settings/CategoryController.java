package com.budgetfy.app.controller.settings;

import com.budgetfy.app.config.swagger.OpenApi;
import com.budgetfy.app.payload.dto.CategoryDTO;
import com.budgetfy.app.payload.response.ApiResponse;
import com.budgetfy.app.service.impl.CategoryServiceImpl;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/settings/categories")
@SecurityRequirement(name = OpenApi.BEARER)
public class CategoryController {

    private final CategoryServiceImpl categoryService;

    @PostMapping
    public ResponseEntity<ApiResponse> create(@Valid @RequestBody CategoryDTO categoryDTO) {
        ApiResponse response = categoryService.create(categoryDTO);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("{categoryId}")
    public ResponseEntity<ApiResponse> get(@PathVariable Integer categoryId) {
        ApiResponse response = categoryService.getDataById(categoryId);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse> get() {
        ApiResponse response = categoryService.getCategories();
        return ResponseEntity.status(response.getStatus()).body(response);
    }
// Only Admin
    @PutMapping("{categoryId}")
    public ResponseEntity<ApiResponse> update(@PathVariable Integer categoryId, @Valid @RequestBody CategoryDTO categoryDTO) {
        ApiResponse response = categoryService.update(categoryId, categoryDTO);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

// Only Admin
    @DeleteMapping("{categoryId}")
    public ResponseEntity<ApiResponse> delete(@PathVariable Integer categoryId) {
        ApiResponse response = categoryService.delete(categoryId);
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}
