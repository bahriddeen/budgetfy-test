package com.budgetfy.app.repository;

import com.budgetfy.app.model.base.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Integer> {
    void deleteAllByUserId(Integer userId);
    Optional<Token> findByToken(String token);
    @Query(value = """
            select t from Token t inner join User  u\s
            on t.user.id = u.id\s
            where u.id = :userId and (t.expired = false or t.revoked = false)\s
            """)
    List<Token> findAllValidTokenByUser(@Param("userId") Integer userId);
}
