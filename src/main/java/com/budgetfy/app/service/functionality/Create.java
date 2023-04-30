package com.budgetfy.app.service.functionality;

import com.budgetfy.app.payload.response.ApiResponse;

/**
 * A generic interface for creating objects of a given type.
 *
 * @param <Object> the type of DTO used to create objects
 */
public interface Create<Object> {
    /**
     * Creates a new object from the given DTO.
     *
     * @param object the DTO used to create the object
     *
     * @return an ApiResponse indicating success or failure, and possibly returning the created object
     */
    ApiResponse create(Object object);
}
