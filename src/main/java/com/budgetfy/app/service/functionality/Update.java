package com.budgetfy.app.service.functionality;

import com.budgetfy.app.payload.response.ApiResponse;

/**
 * A generic interface for updating objects of a given type.
 *
 * @param <DTO> the type of DTO used to update objects
 */
public interface Update<DTO> {
    /**
     * Updates an object with the given ID using the data from the given DTO.
     *
     * @param id the ID of the object to update
     * @param dto the DTO used to update the object
     * @return an ApiResponse indicating success or failure, and possibly returning the updated object
     */
    ApiResponse update(Integer id, DTO dto);
}
