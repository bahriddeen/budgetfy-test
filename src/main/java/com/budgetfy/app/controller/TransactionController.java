package com.budgetfy.app.controller;

import com.budgetfy.app.config.swagger.OpenApi;
import com.budgetfy.app.payload.ItemList;
import com.budgetfy.app.payload.dto.TransactionDTO;
import com.budgetfy.app.payload.response.ApiResponse;
import com.budgetfy.app.service.impl.TransactionServiceImpl;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/transaction")
@SecurityRequirement(name = OpenApi.BEARER)
public class TransactionController {


    private final TransactionServiceImpl transactionService;

    @PostMapping
    public ResponseEntity<ApiResponse> create(@Valid @RequestBody TransactionDTO transactionDTO) {
        ApiResponse response = transactionService.create(transactionDTO);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("{transactionId}")
    public ResponseEntity<ApiResponse> get(@PathVariable Integer transactionId) {
        ApiResponse response = transactionService.getDataById(transactionId);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PutMapping("{transactionId}")
    public ResponseEntity<ApiResponse> update(@PathVariable Integer transactionId, @Valid @RequestBody TransactionDTO transactionDTO) {
        ApiResponse response = transactionService.update(transactionId, transactionDTO);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @DeleteMapping("{transactionId}")
    public ResponseEntity<ApiResponse> delete(@PathVariable Integer transactionId) {
        ApiResponse response = transactionService.delete(transactionId);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @DeleteMapping
    public ResponseEntity<ApiResponse> deleteAllById(@RequestBody ItemList itemList) {
        ApiResponse response = transactionService.deleteAllById(itemList);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("account/{accountId}")
    public ResponseEntity<ApiResponse> loadTransactionsByAccountId(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "50") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy,
            @PathVariable Integer accountId
    ) {
        ApiResponse response = transactionService.loadTransactionsByAccountId(accountId, pageNo, pageSize, sortBy);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse> loadTransactions(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "50") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy
    ) {
        ApiResponse response = transactionService.loadTransactions(pageNo, pageSize, sortBy);
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}
