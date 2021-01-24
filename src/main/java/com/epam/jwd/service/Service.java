package com.epam.jwd.service;

import com.epam.jwd.exception.ServiceException;

/**
 * Base interface for application services
 *
 * @author Egor Miheev
 * @version 1.0.0
 */
public interface Service<T> {

    /**
     * Add entity
     *
     * @param entity object
     * @return true if entity is added
     * @throws ServiceException if fail to retrieve data from DB
     */
    boolean create(T entity) throws ServiceException;

    /**
     * Update entity
     *
     * @param entity object
     * @return true if entity is updated
     * @throws ServiceException if fail to retrieve data from DB
     */
    boolean update(T entity) throws ServiceException;

    /**
     * Delete entity
     *
     * @param entity object
     * @return true if entity is deleted
     * @throws ServiceException if fail to retrieve data from DB
     */
    boolean delete(T entity) throws ServiceException;
}
