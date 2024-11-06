package com.example.demo.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class RegisterRequest {
    @Email(message = "{bad.email}")
    @NotBlank(message = "{required}")
    private String email;


    @Size(min = 6, message = "{Size.password}")
    @NotBlank(message = "{required}")
    private String password;

    @NotBlank(message = "{required}")
    private String confirmPassword;

    @NotBlank(message = "{required}")
    private String name;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isPasswordEqualToConfirmPassword() {
        return password.equals(confirmPassword);
    }
}

