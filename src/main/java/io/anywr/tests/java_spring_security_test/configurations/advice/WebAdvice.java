package io.anywr.tests.java_spring_security_test.configurations.advice;

import io.anywr.tests.java_spring_security_test.dtos.exception.ExceptionDetails;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * A custom error handling for REST.
 */
@RestControllerAdvice
public class WebAdvice extends ResponseEntityExceptionHandler {

    private ExceptionDetails buildBody(HttpStatus status, Exception exception, String path) {
        ExceptionDetails details = new ExceptionDetails();
        details.setStatus(status.value());
        details.setMessage(exception.getLocalizedMessage());
        details.setError(status.getReasonPhrase());
        details.setPath(path);

        return details;
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<Object> handleUsernameNotFoundException(UsernameNotFoundException exception,
                                                                  WebRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String path = ((ServletWebRequest) request).getRequest().getRequestURI();

        ExceptionDetails details = buildBody(status, exception, path);
        return handleExceptionInternal(exception, details, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Object> handleBadCredentialsException(BadCredentialsException exception, WebRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String path = ((ServletWebRequest) request).getRequest().getRequestURI();

        ExceptionDetails details = buildBody(status, exception, path);
        return handleExceptionInternal(exception, details, new HttpHeaders(), status, request);
    }

}
