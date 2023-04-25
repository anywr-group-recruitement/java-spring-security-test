package io.anywr.tests.java_spring_security_test.controllers;

import io.anywr.tests.java_spring_security_test.configurations.security.JwtTokenUtil;
import io.anywr.tests.java_spring_security_test.dtos.auth.AuthContext;
import io.anywr.tests.java_spring_security_test.dtos.auth.AuthPayload;
import io.anywr.tests.java_spring_security_test.dtos.auth.SignUpPayload;
import io.anywr.tests.java_spring_security_test.dtos.profile.UserDto;
import io.anywr.tests.java_spring_security_test.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

import static io.anywr.tests.java_spring_security_test.utils.SecurityConstants.TOKEN_VALIDITY;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private IUserService userService;

    @PostMapping("/login")
    public ResponseEntity<AuthContext> signIn(@RequestBody @Validated AuthPayload payload) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(payload.getUsername(), payload.getPassword()));

            String token = jwtTokenUtil.generateToken(authentication.getName());
            AuthContext authContext = new AuthContext();
            authContext.setToken(token);
            authContext.setExpireAt(new Date(System.currentTimeMillis() + TOKEN_VALIDITY * 1000));
            return ResponseEntity.ok(authContext);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException(String.format("Invalid username/password supplied: %s", e.getLocalizedMessage()));
        }
    }

    @PostMapping(path = {"/register", "signup"})
    public ResponseEntity<UserDto> register(@RequestBody @Validated SignUpPayload payload) {
        return ResponseEntity.ok(userService.register(payload));
    }

}
