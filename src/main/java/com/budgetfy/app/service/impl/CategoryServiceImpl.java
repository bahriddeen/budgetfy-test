package com.budgetfy.app.service.impl;

import com.budgetfy.app.mapstruct.CategoryMapper;
import com.budgetfy.app.model.Category;
import com.budgetfy.app.payload.dto.CategoryDTO;
import com.budgetfy.app.payload.response.ApiResponse;
import com.budgetfy.app.repository.CategoryRepository;
import com.budgetfy.app.service.CategoryService;
import com.budgetfy.app.service.functionality.Create;
import com.budgetfy.app.service.functionality.Delete;
import com.budgetfy.app.service.functionality.Read;
import com.budgetfy.app.service.functionality.Update;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.budgetfy.app.enums.Message.*;
import static com.budgetfy.app.utils.constants.Constants.*;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl
        implements
        CategoryService,
        Create<CategoryDTO>,
        Update<CategoryDTO>,
        Delete<Integer>,
        Read<Integer> {

    private final CategoryMapper categoryMapper;
    private final CategoryRepository categoryRepository;

    @Override
    public ApiResponse create(CategoryDTO categoryDTO) {

        boolean exists = categoryRepository.existsByName(categoryDTO.name());

        if (exists)
            return ApiResponse.error(
                    CATEGORY_ALREADY_EXISTS.message(),
                    HttpStatus.CONFLICT.value()
            );

        Category category = categoryMapper.mapDTOToEntity(categoryDTO);

        categoryRepository.save(category);

        return ApiResponse.success(
                CATEGORY_CREATED.message(),
                HttpStatus.CREATED.value()
        );

    }

    @Override
    public ApiResponse getDataById(Integer categoryId) {
        return categoryRepository.findById(categoryId)
                .map(
                        category ->
                                ApiResponse.success(
                                        categoryMapper.mapEntityToDTO(category),
                                        HttpStatus.OK.value()
                                )
                )
                .orElse(
                        ApiResponse.error(
                                CATEGORY_NOT_FOUND.message(),
                                HttpStatus.NOT_FOUND.value()
                        )
                );
    }

    @Override
    public ApiResponse update(Integer categoryId, CategoryDTO categoryDTO) {

        ApiResponse validResponse = validate(categoryId);

        if (validResponse.isSuccess()) {

            Category category = categoryMapper.mapDTOToEntity(categoryDTO);

            categoryRepository.save(category);

            return ApiResponse.success(
                    CATEGORY_UPDATED.message(),
                    HttpStatus.OK.value()
            );

        }

        return validResponse;

    }

    @Override
    public ApiResponse delete(Integer categoryId) {

        ApiResponse validResponse = validate(categoryId);

        if (validResponse.isSuccess()) {

            categoryRepository.deleteById(categoryId);

            return ApiResponse.success(
                    CATEGORY_DELETED.message(),
                    HttpStatus.NO_CONTENT.value()
            );

        }

        return validResponse;

    }

    @Override
    public ApiResponse getCategories() {

        List<CategoryDTO> categories = categoryMapper.listMapEntityToDTO(categoryRepository.findAllByShowTrue());

        return ApiResponse.success(
                categories,
                HttpStatus.OK.value()
        );

    }

    private ApiResponse validate(Integer categoryId) {

        Optional<Category> category = categoryRepository.findById(categoryId);

        if (category.isEmpty())
            return ApiResponse.success(
                    CATEGORY_NOT_FOUND.message(),
                    HttpStatus.NOT_FOUND.value()
            );

        Category _category = category.get();

        if (_category.getParent() == null)
            return ApiResponse.error(
                    CATEGORY_CONFLICT.message(),
                    HttpStatus.CONFLICT.value()
            );

        return ApiResponse.success();

    }

}
