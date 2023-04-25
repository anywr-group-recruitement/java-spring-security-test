package io.anywr.tests.java_spring_security_test.models;

import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.util.Date;

/**
 * Base model to implement audit and soft-delete for an entity.
 */
@MappedSuperclass
public abstract class BaseModel {

    @ColumnDefault("NOW()")
    @Column(updatable = false)
    private Date createdAt = new Date();

    @ColumnDefault("false")
    private Boolean deleted = Boolean.FALSE;

    public Date getCreatedAt() {
        return createdAt;
    }

    public boolean isDeleted() {
        if (deleted == null) {
            deleted = false;
        }
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }
}
