package com.budgetfy.app.mapstruct.base;

import com.budgetfy.app.model.Account;
import com.budgetfy.app.model.Category;
import com.budgetfy.app.model.User;

import java.util.List;

/**
 * An interface defining methods for converting (DTOs) to Entity objects and vice versa.
 *
 * @param <DTO>    the type of DTO
 * @param <Entity> the type of Entity
 */
public interface ObjectMapper<DTO, Entity> {
    /**
     * Converts the given DTO to an Entity object.
     *
     * @param dto the DTO to convert
     *
     * @return the Entity object
     */
    Entity mapDTOToEntity(DTO dto);

    /**
     * Converts the given Entity object to a DTO.
     *
     * @param entity the Entity object to convert
     *
     * @return the DTO
     */
    DTO mapEntityToDTO(Entity entity);

    /**
     * Converts a list of DTOs to a list of Entity objects.
     *
     * @param dtoList the list of DTOs to convert
     *
     * @return the list of Entity objects
     */
    List<Entity> listMapDTOToEntity(List<DTO> dtoList);

    /**
     * Converts a list of Entity objects to a list of DTOs.
     *
     * @param entityList the list of Entity objects to convert
     *
     * @return the list of DTOs
     */
    List<DTO> listMapEntityToDTO(List<Entity> entityList);

    default User userId(Integer userId) {

        if (userId == null) return null;

        User user = new User();
        user.setId(userId);

        return user;

    }

    default Account accountId(Integer accountId) {

        if (accountId == null)
            return null;

        Account account = new Account();
        account.setId(accountId);

        return account;

    }

    default Category categoryId(Integer categoryId) {

        if (categoryId == null)
            return null;

        Category category = new Category();
        category.setId(categoryId);

        return category;

    }
}
