package io.anywr.tests.java_spring_security_test.repositories;

import org.springframework.stereotype.Repository;

import io.anywr.tests.java_spring_security_test.models.User;

@Repository
public interface IUserRepository extends IBaseRepository<User, String> {
}