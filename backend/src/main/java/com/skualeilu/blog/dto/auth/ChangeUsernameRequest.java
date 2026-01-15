package com.skualeilu.blog.dto.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ChangeUsernameRequest {

    @NotBlank(message = "New username is required")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    private String newUsername;

    @NotBlank(message = "Current password is required")
    private String password;

    public String getNewUsername() {
        return newUsername;
    }

    public void setNewUsername(String newUsername) {
        this.newUsername = newUsername;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
