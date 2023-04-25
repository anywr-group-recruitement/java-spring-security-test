package io.anywr.tests.java_spring_security_test.services;

import io.anywr.tests.java_spring_security_test.dtos.auth.SignUpPayload;
import io.anywr.tests.java_spring_security_test.dtos.profile.UserDto;
import io.anywr.tests.java_spring_security_test.exceptions.AlreadyExistsException;
import io.anywr.tests.java_spring_security_test.models.User;
import io.anywr.tests.java_spring_security_test.repositories.IUserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;

class UserServiceTest {
    @InjectMocks
    private IUserService userService = new UserService();

    @Mock
    private IUserRepository userRepository;

    @Spy
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private AutoCloseable autoCloseable;

    @BeforeEach
    public void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);

        // Init context
        SecurityContextHolder.clearContext();
    }

    @AfterEach
    public void destroy() throws Exception {
        if (autoCloseable != null) {
            autoCloseable.close();
        }
    }

    @Test
    public void when_get_dto_has_null_user() {
        // Given
        User user = null;

        // When

        // Then
        UserDto result = userService.getDto(user);
        assertNull(result);

        verifyNoInteractions(userRepository);
    }

    @Test
    public void when_get_dto_has_user() {
        // Given
        User user = new User();

        // When

        // Then
        UserDto result = userService.getDto(user);
        assertNotNull(result);

        verifyNoInteractions(userRepository);
    }

    @Test
    public void when_me_has_result() {
        // Given
        User user = new User();
        user.setUsername(UUID.randomUUID().toString());

        SecurityContextHolder.createEmptyContext();
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
        SecurityContextHolder.getContext().setAuthentication(token);

        // When

        // Then
        UserDto result = userService.me();
        assertNotNull(result);

        verifyNoInteractions(userRepository);
    }

    @Test
    public void when_user_not_authenticated() {
        // Given
        SecurityContextHolder.createEmptyContext();

        // When

        // Then
        UserDto result = userService.me();
        assertNull(result);

        verifyNoInteractions(userRepository);
    }

    @Test
    public void when_register_with_exists_username() {
        // Given
        SignUpPayload payload = new SignUpPayload();
        payload.setUsername(UUID.randomUUID().toString());

        // When
        doReturn(true).when(userRepository).existsById(payload.getUsername());

        // Then
        assertThrows(AlreadyExistsException.class, () -> userService.register(payload), "User already exists.");

        verify(userRepository, times(1)).existsById(payload.getUsername());
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    public void when_register_without_password() {
        // Given
        SignUpPayload payload = new SignUpPayload();
        payload.setUsername(UUID.randomUUID().toString());

        // When

        // Then
        assertThrows(BadCredentialsException.class, () -> userService.register(payload), "Password not valid.");

        verify(userRepository, times(1)).existsById(payload.getUsername());
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    public void when_register_done() {
        // Given
        SignUpPayload payload = new SignUpPayload();
        payload.setUsername(UUID.randomUUID().toString());
        payload.setPassword(UUID.randomUUID().toString());

        // When

        // Then
        UserDto result = userService.register(payload);
        assertNotNull(result);

        verify(userRepository, times(1)).existsById(payload.getUsername());
        verify(passwordEncoder, times(1)).encode(anyString());
    }

}
