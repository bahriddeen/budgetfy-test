package com.budgetfy.app.repository;

import com.budgetfy.app.model.Transaction;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    Slice<Transaction> findTransactionByAccountId(Integer accountId, Pageable pageable);
}
