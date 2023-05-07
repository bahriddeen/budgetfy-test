package com.budgetfy.app.payload.dto;

import com.budgetfy.app.enums.PaymentType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record TemplateDTO(
        Integer id,
        @NotNull(message = "UserId cannot be null")
        Integer userId,
        @NotNull(message = "AccountId cannot be null")
        Integer accountId,
        @NotNull(message = "AccountId cannot be null")
        Integer categoryId,
        @NotBlank(message = "Name cannot be empty")
        String name,
        String note,
        @NotNull(message = "Payment type cannot be null")
        PaymentType paymentType,
        double amount,
        boolean expense
        ) {
}
