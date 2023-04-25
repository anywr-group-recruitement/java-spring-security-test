package io.anywr.tests.java_spring_security_test.dtos.exception;

import java.time.LocalDateTime;

/**
 * a JSON response for RESTful present the Whitelabel Error Page.
 */
public class ExceptionDetails {
    private Integer status;
    private final LocalDateTime timestamp = LocalDateTime.now();

    private String error;
    private String message;
    private String path;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

}
