package io.anywr.tests.java_spring_security_test.dtos.auth;

import java.util.Date;

public class AuthContext {
    private String token;
    private Date expireAt;
    
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getExpireAt() {
        return expireAt;
    }

    public void setExpireAt(Date expireAt) {
        this.expireAt = expireAt;
    }
}
