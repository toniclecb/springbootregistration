package com.toniclecb.springbootregistration.domain.h2.domain;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity; // this is being replaced by jakarta
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "register")
public class RegisterH2 implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    // GeneratedValue is not used here because we get the ID from mysql
    @Column(name = "id", updatable = false, unique = true, nullable = false, columnDefinition = "varchar(36)")
    // Type and columnDefinition will make the column database be varchar instead of binary
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID id;

    @Column(nullable = false, length = 80)
    private String name;

    public RegisterH2() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

