package io.anywr.tests.java_spring_security_test.configurations.security;

import io.anywr.tests.java_spring_security_test.models.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

class JwtTokenFilterTest {
    @InjectMocks
    private JwtTokenFilter jwtTokenFilter = new JwtTokenFilter();

    @InjectMocks
    private JwtTokenUtil jwtTokenUtil = new JwtTokenUtil();

    @Mock
    private UserDetailsService userDetailsService;

    private AutoCloseable autoCloseable;

    @BeforeEach
    public void setUp() throws Exception {
        jwtTokenUtil = spy(jwtTokenUtil);

        autoCloseable = MockitoAnnotations.openMocks(this);

        // Common given
        jwtTokenUtil.afterPropertiesSet();
        SecurityContextHolder.clearContext();
    }

    @AfterEach
    public void destroy() throws Exception {
        if (autoCloseable != null) {
            autoCloseable.close();
        }
    }

    @Test
    public void when_user_without_token() throws Exception {
        // Given
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockFilterChain filterChain = new MockFilterChain();

        // When

        // Then
        jwtTokenFilter.doFilterInternal(request, response, filterChain);
        assertNotNull(SecurityContextHolder.getContext());
        assertNull(SecurityContextHolder.getContext().getAuthentication());

        verifyNoInteractions(userDetailsService);

        verify(jwtTokenUtil, times(1)).afterPropertiesSet();
        verifyNoMoreInteractions(jwtTokenUtil);
    }

    @Test
    public void when_user_with_token_header() throws ServletException, IOException {
        // Given
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockFilterChain filterChain = new MockFilterChain();

        String username = UUID.randomUUID().toString();
        String token = jwtTokenUtil.generateToken(username);
        request.addHeader(AUTHORIZATION, token);

        User user = new User();

        // When
        doReturn(user).when(userDetailsService).loadUserByUsername(username);

        // Then
        jwtTokenFilter.doFilterInternal(request, response, filterChain);
        assertNotNull(SecurityContextHolder.getContext());
        assertNotNull(SecurityContextHolder.getContext().getAuthentication());

        verify(userDetailsService, times(1)).loadUserByUsername(username);
        verify(jwtTokenUtil, times(1)).getUsernameFromToken(token);
    }

    @Test
    public void when_user_with_token_using_parameters() throws ServletException, IOException {
        // Given
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockFilterChain filterChain = new MockFilterChain();

        String username = UUID.randomUUID().toString();
        String token = jwtTokenUtil.generateToken(username);
        request.addParameter(AUTHORIZATION, token);

        User user = new User();

        // When
        doReturn(user).when(userDetailsService).loadUserByUsername(username);

        // Then
        jwtTokenFilter.doFilterInternal(request, response, filterChain);
        assertNotNull(SecurityContextHolder.getContext());
        assertNotNull(SecurityContextHolder.getContext().getAuthentication());

        verify(userDetailsService, times(1)).loadUserByUsername(username);
        verify(jwtTokenUtil, times(1)).getUsernameFromToken(token);
    }

    @Test
    public void when_token_with_invalid_user() throws ServletException, IOException {
        // Given
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockFilterChain filterChain = new MockFilterChain();

        String username = UUID.randomUUID().toString();
        String token = jwtTokenUtil.generateToken(username);
        request.addParameter(AUTHORIZATION, token);

        // When
        doThrow(UsernameNotFoundException.class).when(userDetailsService).loadUserByUsername(username);

        // Then
        jwtTokenFilter.doFilterInternal(request, response, filterChain);
        assertNotNull(SecurityContextHolder.getContext());
        assertNull(SecurityContextHolder.getContext().getAuthentication());

        verify(userDetailsService, times(1)).loadUserByUsername(username);
        verify(jwtTokenUtil, times(1)).getUsernameFromToken(token);
    }

    @Test
    public void when_token_not_valid() throws ServletException, IOException {
        // Given
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockFilterChain filterChain = new MockFilterChain();

        String username = UUID.randomUUID().toString();
        String token = UUID.randomUUID().toString();
        request.addHeader(AUTHORIZATION, token);

        // When

        // Then
        jwtTokenFilter.doFilterInternal(request, response, filterChain);
        assertNotNull(SecurityContextHolder.getContext());
        assertNull(SecurityContextHolder.getContext().getAuthentication());

        verify(userDetailsService, never()).loadUserByUsername(username);
        verify(jwtTokenUtil, times(1)).getUsernameFromToken(token);
    }
}
