package io.anywr.tests.java_spring_security_test.dtos.auth;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class SignUpPayload {

    @NotEmpty(message = "Provide username.")
    private String username;
    @NotEmpty(message = "Provide a valid password.")
    @Size(min = 14, message = "{validation.name.size.too_short}") // NIST recommendation
    private String password;

    @NotEmpty(message = "Provide a name.")
    private String firstName;
    @NotEmpty(message = "Provide last name.")
    private String lastName;
    private String phoneNumber;

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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
