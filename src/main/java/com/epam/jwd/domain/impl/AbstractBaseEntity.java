package com.epam.jwd.domain.impl;

import com.epam.jwd.domain.BaseEntity;

public abstract class AbstractBaseEntity implements BaseEntity {
    private Long id;

    public AbstractBaseEntity() {
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}
