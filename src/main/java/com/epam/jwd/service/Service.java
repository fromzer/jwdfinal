package com.epam.jwd.service;

import com.epam.jwd.exception.ServiceException;

public interface Service<T> {

    boolean create(T entity) throws ServiceException;

    boolean update(T entity) throws ServiceException;

    boolean delete(T entity) throws ServiceException;
}
