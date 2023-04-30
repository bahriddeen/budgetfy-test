package com.budgetfy.app.repository;

import com.budgetfy.app.model.Account;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    int countAccountsByUserId(Integer userId);
    void deleteAllByUserId(Integer userId);
    List<Account> findAccountsByUserId(Integer userId, Sort sort);
    boolean existsByNameAndUserId(String accountName, Integer userId);
    List<Account> searchAccountByNameContainingIgnoreCaseAndUserId(String accountName, Integer userId);
}
