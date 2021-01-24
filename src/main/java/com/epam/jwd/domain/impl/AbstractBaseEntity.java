package com.epam.jwd.domain.impl;

import com.epam.jwd.domain.BaseEntity;

/**
 * Base class for application entities
 * 
 * @author Egor Miheev
 * @version 1.0.0
 */
public abstract class AbstractBaseEntity implements BaseEntity {
    
    /**
     * Unique id for application entities. 
     * Identifies record in DB
     */
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
