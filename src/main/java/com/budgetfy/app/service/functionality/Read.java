package com.budgetfy.app.service.functionality;

import com.budgetfy.app.payload.response.ApiResponse;

/**
 * A generic interface for reading objects of a given type.
 *
 * @param <Id> the type of the object's ID or primary key
 */
public interface Read<Id> {
    /**
     * Retrieves an object with the given ID.
     *
     * @param id the ID of the object to retrieve
     *
     * @return an ApiResponse indicating success or failure, and possibly returning the retrieved object
     */
    ApiResponse getDataById(Id id);

}
