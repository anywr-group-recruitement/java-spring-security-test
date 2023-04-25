package io.anywr.tests.java_spring_security_test.dtos.auth;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class AuthPayload {

    @NotEmpty(message = "Provide username.")
    private String username;

    @NotEmpty(message = "Provide a valid password.")
    @Size(min = 14, message = "{validation.name.size.too_short}") // NIST recommendation
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }    
}
