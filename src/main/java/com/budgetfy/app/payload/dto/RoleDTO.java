package com.budgetfy.app.payload.dto;

import com.budgetfy.app.enums.RoleType;
import jakarta.validation.constraints.NotNull;

public record RoleDTO(
        Integer id,
        @NotNull(message = "Role name cannot be null")
        RoleType role,
        boolean active
) {}



