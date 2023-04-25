package io.anywr.tests.java_spring_security_test.services;

import io.anywr.tests.java_spring_security_test.dtos.auth.SignUpPayload;
import io.anywr.tests.java_spring_security_test.dtos.profile.UserDto;
import io.anywr.tests.java_spring_security_test.exceptions.AlreadyExistsException;
import io.anywr.tests.java_spring_security_test.models.User;
import io.anywr.tests.java_spring_security_test.repositories.IUserRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Custom implementation of user-details for spring-security.
 */
@Service
public class UserService implements UserDetailsService, IUserService {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(@NonNull String username) throws UsernameNotFoundException {
        User user = userRepository.findById(username).orElse(null);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("Username %s not found.", username));
        }

        // Lazy load roles
        // - use @cacheable instead
        // - second-cache level
        if (!Hibernate.isInitialized(user.getRoles())) {
            Hibernate.initialize(user.getRoles());
        }
        return user;
    }

    @Override
    public UserDto getDto(@Nullable User user) {
        if (user == null) {
            return null;
        }

        UserDto dto = new UserDto();
        dto.setUsername(user.getUsername());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setPhoneNumber(user.getPhoneNumber());
        return dto;
    }

    @Override
    @Nullable
    public UserDto me() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        if (securityContext != null) {
            Authentication authentication = securityContext.getAuthentication();

            User user = null;
            if (authentication != null && authentication.isAuthenticated()) {
                if (authentication.getPrincipal() instanceof User) {
                    user = (User) authentication.getPrincipal();
                }

                if (user != null) {
                    return getDto(user);
                }
            }
        }

        return null;
    }

    @Override
    public UserDto register(@NonNull SignUpPayload payload) {
        if (userRepository.existsById(payload.getUsername())) {
            throw new AlreadyExistsException("User already exists.");
        }
        if (payload.getPassword() == null || payload.getPassword().length() == 0) {
            // For junit - spring-validator has this through javax.constraints
            throw new BadCredentialsException("Password not valid.");
        }

        User user = new User();
        user.setUsername(payload.getUsername());
        user.setFirstName(payload.getFirstName());
        user.setLastName(payload.getLastName());
        user.setPhoneNumber(payload.getPhoneNumber());

        user.setPassword(passwordEncoder.encode(payload.getPassword()));
        // TODO: Add default role.
        userRepository.save(user);
        return getDto(user);
    }
}
