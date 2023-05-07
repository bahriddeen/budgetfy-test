package com.budgetfy.app.utils;

import com.budgetfy.app.model.Transaction;
import com.budgetfy.app.repository.TransactionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public final class TransactionUtils {

    private TransactionUtils() {
        throw new AssertionError("Cannot instantiate utility class");
    }

    public static void deleteTransactionsInBatch(TransactionRepository transactionRepository, Integer accountId) {
        int batchSize = 1000;
        int page = 0;

        while (true) {
            Pageable pageable = PageRequest.of(page, batchSize);
            Page<Transaction> transactions = transactionRepository.findByAccountId(accountId, pageable);

            if (transactions.isEmpty()) {
                break;
            }

            transactionRepository.deleteAllInBatch(transactions.getContent());

            page++;
        }
    }
}
