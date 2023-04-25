package io.anywr.tests.java_spring_security_test.controllers;

import io.anywr.tests.java_spring_security_test.dtos.profile.UserDto;
import io.anywr.tests.java_spring_security_test.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private IUserService userService;

    @GetMapping("/me")
    public ResponseEntity<UserDto> me() {
        UserDto dto = userService.me();
        if (dto == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(dto);
    }

}
