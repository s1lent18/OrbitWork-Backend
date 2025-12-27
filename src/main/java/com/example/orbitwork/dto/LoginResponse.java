package com.example.orbitwork.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class LoginResponse {

    @NotNull(message = "Id must not be null")
    @Positive(message = "Id must be a positive number")
    private Long id;

    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 100, message = "Username must be between 3 and 100 characters")
    String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    String email;

    @NotBlank(message = "Token not provided")
    String token;

    public LoginResponse(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }
}