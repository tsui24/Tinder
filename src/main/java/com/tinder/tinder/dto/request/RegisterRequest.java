package com.tinder.tinder.dto.request;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    @Size(min = 4, message = "full name phải ít nhất 4 kí tự")
    private String fullName;
    @Size(min = 8, message = "user name phải ít nhất 8 kí tự")
    private String username;
    private String password;
    private String confirmPassword;

    public @Size(min = 4, message = "full name phải ít nhất 4 kí tự") String getFullName() {
        return fullName;
    }

    public void setFullName(@Size(min = 4, message = "full name phải ít nhất 4 kí tự") String fullName) {
        this.fullName = fullName;
    }

    public @Size(min = 8, message = "user name phải ít nhất 8 kí tự") String getUsername() {
        return username;
    }

    public void setUsername(@Size(min = 8, message = "user name phải ít nhất 8 kí tự") String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
