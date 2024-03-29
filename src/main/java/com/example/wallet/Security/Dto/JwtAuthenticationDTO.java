package com.example.wallet.Security.Dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class JwtAuthenticationDTO {

    @NotNull(message = "Informe um email")
    @NotEmpty(message = "Informe um email")
    private String email;

    @NotNull(message = "Informe uma senha")
    @NotEmpty(message = "Informe uma senha")
    private String password;

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
}
