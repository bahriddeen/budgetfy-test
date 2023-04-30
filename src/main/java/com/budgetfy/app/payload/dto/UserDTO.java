package com.budgetfy.app.payload.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserDTO(
        Integer id,
        @NotNull(message = "Role id cannot be null")
        Integer roleId,
        @NotBlank(message = "Email cannot be empty")
        String email,
        boolean active
) {

}
