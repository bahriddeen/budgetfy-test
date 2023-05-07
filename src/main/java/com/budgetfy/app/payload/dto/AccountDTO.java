package com.budgetfy.app.payload.dto;

import com.budgetfy.app.enums.AccountType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AccountDTO(
        Integer id,
        @NotNull(message = "UserId cannot be null") Integer userId,
        @NotBlank(message = "Name cannot be empty") String name,
        @NotBlank(message = "Color cannot be empty") String color,
        @NotBlank(message = "Currency cannot be empty") String currency,
        @NotNull(message = "Account type cannot be null") AccountType accountType,
        double balance,
        boolean showStatistics
) {}
