package com.carmarketpro.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserUpdateRequest {

    private Boolean enabled;

    @Email
    private String email;

    @NotBlank
    private String username;

    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    
}
