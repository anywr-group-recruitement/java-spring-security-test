package io.anywr.tests.java_spring_security_test.repositories;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface IBaseRepository<M, IDType extends Serializable> extends JpaRepository<M, IDType> {
    
}
