package com.budgetfy.app.service;

import com.budgetfy.app.payload.response.ApiResponse;

public interface UserService  {
    ApiResponse getUsers(Integer pageNo, Integer pageSize, String sortBy);
}
