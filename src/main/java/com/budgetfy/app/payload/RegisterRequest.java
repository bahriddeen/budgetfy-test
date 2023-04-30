package com.budgetfy.app.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    @NotBlank(message = "Name cannot be empty")
    private String name;
    @NotBlank(message = "Email cannot be empty")
    @Email(regexp = ".+[@].+[\\.].+", message = "Please provide a valid email address")
    private String email;
    @NotBlank(message = "Password cannot be empty")
    private String password;
}
