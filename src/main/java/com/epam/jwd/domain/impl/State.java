package com.epam.jwd.domain.impl;

import com.epam.jwd.domain.BaseEntity;
import com.epam.jwd.exception.UnknownEntityException;

import java.io.Serializable;

/**
 * State class describes states of requests
 * 
 * @author Egor Miheev
 * @version 1.0.0
 */
public enum State implements BaseEntity, Serializable {
    CONSIDERED(1L), REJECTED(2L), CONFIRMED(3L);

    private static final long serialVersionUID = -1833926400724649771L;

    /**
     * State ID
     */    
    private Long id;

    State(Long id) {
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

    public static State resolveStateById(Long id) throws UnknownEntityException {
        for (State state : State.values()) {
            if (state.getId() == id) {
                return state;
            }
        }
        throw new UnknownEntityException("Such id does not exist");
    }
}
