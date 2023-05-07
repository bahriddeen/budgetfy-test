package com.budgetfy.app.mapstruct;

import com.budgetfy.app.mapstruct.base.ObjectMapper;
import com.budgetfy.app.model.Category;
import com.budgetfy.app.payload.dto.CategoryDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper extends ObjectMapper<CategoryDTO, Category> {

    @Override
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(source = "parentId", target = "parent", qualifiedByName = "parentIdToCategory")
    Category mapDTOToEntity(CategoryDTO categoryDTO);

    @Override
    @Mapping(source = "parent.id", target = "parentId")
    CategoryDTO mapEntityToDTO(Category category);

    @Override
    List<Category> listMapDTOToEntity(List<CategoryDTO> categoryDTOS);

    @Override
    List<CategoryDTO> listMapEntityToDTO(List<Category> categories);
    @Named("parentIdToCategory")
    default Category parentId(Integer parentId) {

        if (parentId == null) return null;


        Category category = new Category();
        category.setId(parentId);

        return category;

    }


}
