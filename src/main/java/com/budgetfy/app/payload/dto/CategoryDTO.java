package com.budgetfy.app.payload.dto;

import jakarta.validation.constraints.NotBlank;

public record CategoryDTO(
        Integer id,
        Integer parentId,
        @NotBlank(message = "Name cannot be empty")
        String name,
        boolean show

) {
}
