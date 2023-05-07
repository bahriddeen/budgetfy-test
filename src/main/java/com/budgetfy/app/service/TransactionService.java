package com.budgetfy.app.service;

import com.budgetfy.app.payload.ItemList;
import com.budgetfy.app.payload.response.ApiResponse;

import java.util.List;

public interface TransactionService {
    ApiResponse deleteByIds(ItemList itemList);
    ApiResponse loadTransactions(Integer pageNo, Integer pageSize, String sortBy, ItemList itemList);
    ApiResponse loadTransactionsByAccountId(Integer accountId, Integer pageNo, Integer pageSize, String sortBy);
}
