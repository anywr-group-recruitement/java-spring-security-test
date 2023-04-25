package io.anywr.tests.java_spring_security_test.models;

import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * This class represents a permission to a resource.
 * A permission represents a particular immutable function.
 */
@Entity
@Where(clause = "deleted = false OR deleted IS NULL")
public class Permission extends BaseModel {
    @Id
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
