package io.anywr.tests.java_spring_security_test.configurations.security;

import io.jsonwebtoken.JwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Configuration
public class JwtTokenFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenFilter.class);

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        String token = request.getHeader(AUTHORIZATION);
        if (token == null) {
            // Give a chance to params for forms
            token = request.getParameter(AUTHORIZATION);
        }
        if (token != null) {
            token = token.replace("Bearer ", "");

            try {
                String username = jwtTokenUtil.getUsernameFromToken(token);
                UserDetails user = userDetailsService.loadUserByUsername(username);

                if (user != null) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (JwtException e) {
                LOGGER.error(String.format("Errors while parsing token: %s", e.getLocalizedMessage()));
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug(String.format("Errors while parsing token [value = %s]", token), e);
                }
            } catch (UsernameNotFoundException e) {
                LOGGER.error(String.format("User not found: %s", e.getLocalizedMessage()));
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug(String.format("User not found while parsing token [value = %s]", token), e);
                }
            }
        }

        filterChain.doFilter(request, response);
    }

}
