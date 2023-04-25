package io.anywr.tests.java_spring_security_test.services;

import io.anywr.tests.java_spring_security_test.dtos.auth.AuthContext;
import io.anywr.tests.java_spring_security_test.dtos.auth.SignUpPayload;
import io.anywr.tests.java_spring_security_test.dtos.profile.UserDto;
import io.anywr.tests.java_spring_security_test.models.User;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

public interface IUserService {

    UserDto getDto(@Nullable User user);

    @Nullable
    UserDto me();

    UserDto register(@NonNull SignUpPayload payload);

}
