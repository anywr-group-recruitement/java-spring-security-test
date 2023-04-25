package io.anywr.tests.java_spring_security_test.models;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@Where(clause = "deleted = false OR deleted IS NULL")
public class Role extends BaseModel {
    @Id
    private String name;

    @ColumnDefault("0")
    private int priority = 0;

    @ManyToMany
    private List<Role> childs = new ArrayList<>();

    @ManyToMany
    private List<Permission> permissions = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public List<Role> getChilds() {
        if (childs == null) {
            childs = new ArrayList<>();
        }
        return childs;
    }

    public void addChild(Role role) {
        if (role == null) {
            return;
        }
        this.getChilds().add(role);
    }

    public void setChilds(List<Role> childs) {
        this.childs = childs;
    }

    public List<Permission> getPermissions() {
        if (permissions == null) {
            permissions = new ArrayList<>();
        }
        return permissions;
    }

    public void addPermission(Permission permission) {
        if (permission == null) {
            return;
        }
        this.getPermissions().add(permission);
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }
}
