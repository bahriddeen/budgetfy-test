package com.budgetfy.app.repository;

import com.budgetfy.app.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);
    List<User> findAllByActiveIsTrue(Pageable pageable);
    List<User> findAllByActiveIsFalseAndCreatedAtBefore(Instant dateTime);

}
