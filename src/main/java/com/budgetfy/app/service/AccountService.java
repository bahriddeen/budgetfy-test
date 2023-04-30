package com.budgetfy.app.service;

import com.budgetfy.app.payload.response.ApiResponse;

public interface AccountService {
    ApiResponse getUserAccounts(Integer userId);

    ApiResponse searchByName(String accountName);
}
