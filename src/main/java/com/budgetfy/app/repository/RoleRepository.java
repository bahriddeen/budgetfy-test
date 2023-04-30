package com.budgetfy.app.repository;

import com.budgetfy.app.enums.RoleType;
import com.budgetfy.app.model.base.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByRole(RoleType roleType);
}
