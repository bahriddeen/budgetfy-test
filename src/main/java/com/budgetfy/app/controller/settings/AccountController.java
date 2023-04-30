package com.budgetfy.app.controller.settings;

import com.budgetfy.app.config.swagger.OpenApi;
import com.budgetfy.app.payload.dto.AccountDTO;
import com.budgetfy.app.payload.response.ApiResponse;
import com.budgetfy.app.service.impl.AccountServiceImpl;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/settings/account")
@SecurityRequirement(name = OpenApi.BEARER)
public class AccountController {

    private final AccountServiceImpl accountService;

    @PostMapping
    public ResponseEntity<ApiResponse> create(@Valid @RequestBody AccountDTO accountDTO) {
        ApiResponse response = accountService.create(accountDTO);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("{accountId}")
    public ResponseEntity<ApiResponse> get(@PathVariable Integer accountId) {
        ApiResponse response = accountService.getDataById(accountId);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PutMapping("{accountId}")
    public ResponseEntity<ApiResponse> update(@PathVariable Integer accountId, @Valid @RequestBody AccountDTO accountDTO) {
        ApiResponse response = accountService.update(accountId, accountDTO);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @DeleteMapping("{accountId}")
    public ResponseEntity<ApiResponse> delete(@PathVariable Integer accountId) {
        ApiResponse response = accountService.delete(accountId);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @DeleteMapping("user/{userId}")
    public void deleteUserAccounts(@PathVariable Integer userId) {
        accountService.deleteUserAccounts(userId);
    }

    @GetMapping("user-accounts/{userId}")
    public ResponseEntity<ApiResponse> getUserAccounts(@PathVariable Integer userId) {
        ApiResponse response = accountService.getUserAccounts(userId);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/search/{name}")
    public ResponseEntity<ApiResponse> searchTemplate(@PathVariable String name) {
        ApiResponse response = accountService.searchByName(name);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

}
