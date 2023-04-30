package com.budgetfy.app.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
    @NotBlank(message = "Email cannot be empty")
    @Email(regexp = ".+[@].+[\\.].+", message = "Please provide a valid email address")
    private String email;
    @NotBlank(message = "Password cannot be empty")
    @Size(min = 4, message = "Password must be at least 4 characters long")
    private String password;

}
