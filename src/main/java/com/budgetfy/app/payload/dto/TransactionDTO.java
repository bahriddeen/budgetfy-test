package com.budgetfy.app.payload.dto;

import com.budgetfy.app.enums.PaymentStatus;
import com.budgetfy.app.enums.PaymentType;
import com.budgetfy.app.enums.TransactionType;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public record TransactionDTO(
        Integer id,
        @NotNull(message = "AccountId cannot be null")
        Integer accountId,
        Integer t_accountId,
        @NotNull(message = "CategoryId cannot be null")
        Integer categoryId,
        String note,
        String payee,
        double amount,
        @NotNull(message = "Payment type cannot be null")
        PaymentType paymentType,
        @NotNull(message = "Payment status cannot be null")
        PaymentStatus paymentStatus,
        @NotNull(message = "Transaction type cannot be null")
        TransactionType transactionType,
        Date date
) {
}
