package com.budgetfy.app.service.functionality;

import com.budgetfy.app.payload.response.ApiResponse;

/**
 * A generic interface for deleting objects of a given type.
 *
 * @param <Id> the type of the object's ID or primary key
 */
public interface Delete<Id> {
    /**
     * Deletes an object with the given ID.
     *
     * @param id the ID of the object to delete
     *
     * @return an ApiResponse indicating success or failure
     */
    ApiResponse delete(Id id);
}
