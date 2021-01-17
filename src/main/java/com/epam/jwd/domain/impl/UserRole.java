package com.epam.jwd.domain.impl;

import com.epam.jwd.domain.BaseEntity;
import com.epam.jwd.exception.UnknownEntityException;

import java.io.Serializable;

public enum UserRole implements BaseEntity, Serializable {
    ADMIN(1L), USER(2L);

    private static final long serialVersionUID = -4815531461424481102L;

    private Long id;

    UserRole(Long id) {
        this.id = id;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public static UserRole resolveRoleById(Long id) throws UnknownEntityException {
        for (UserRole role : UserRole.values()) {
            if (role.getId() == id) {
                return role;
            }
        }
        throw new UnknownEntityException("Such id does not exist");
    }
}
