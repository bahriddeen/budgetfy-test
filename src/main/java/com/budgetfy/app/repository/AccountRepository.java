package com.budgetfy.app.repository;

import com.budgetfy.app.model.Account;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    int countAccountsByUserId(Integer userId);
    void deleteAllByUserId(Integer userId);
    List<Account> findAccountsByUserId(Integer userId, Sort sort);
    boolean existsByNameAndUserId(String accountName, Integer userId);
    List<Account> searchAccountByNameContainingIgnoreCaseAndUserId(String accountName, Integer userId);
    @Query(value = "SELECT a.id FROM Account a LEFT JOIN Users u " +
            "ON a.user_id = u.id " +
            "WHERE u.id = :userId", nativeQuery = true)
    List<Integer> getUserAccountIds(@Param("userId") Integer userId);

}
