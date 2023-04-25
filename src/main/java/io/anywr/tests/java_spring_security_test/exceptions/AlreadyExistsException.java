package io.anywr.tests.java_spring_security_test.exceptions;

import org.springframework.security.core.AuthenticationException;

/**
 * Thrown if an entity is already exists.
 */
public class AlreadyExistsException extends AuthenticationException {

    private static final long serialVersionUID = -7712347328999957887L;

    public AlreadyExistsException(String message) {
        super(message);
    }
}
