package com.epam.jwd.service.impl;

import com.epam.jwd.controller.command.RequestContext;
import com.epam.jwd.dao.UserDAO;
import com.epam.jwd.domain.impl.User;
import com.epam.jwd.exception.DaoException;
import com.epam.jwd.exception.ServiceException;
import com.epam.jwd.service.Service;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;


public class UserService implements Service<User> {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private static final ReentrantLock reentrantLock = new ReentrantLock();
    private static final AtomicBoolean isCreated = new AtomicBoolean(false);
    private static UserService instance;
    private UserDAO userDAO = UserDAO.getInstance();

    private UserService() {
    }

    public static UserService getInstance() {
        if (!isCreated.get()) {
            reentrantLock.lock();
            try {
                if (instance == null) {
                    instance = new UserService();
                    isCreated.set(true);
                }
            } finally {
                reentrantLock.unlock();
            }
        }
        return instance;
    }

    public List<User> findAllUser() throws ServiceException {
        try {
            return userDAO.findAll();
        } catch (DaoException e) {
            logger.warn("Service findAllUser error");
            throw new ServiceException("Service findAllUser error: " + e);
        }
    }

    public User findUserById(Long id) throws ServiceException {
        try {
            return userDAO.findEntityById(id).orElseThrow();
        } catch (DaoException e) {
            logger.warn("Service findUserById error");
            throw new ServiceException("Service findUserById error: " + e);
        }
    }

    public boolean checkPassword(RequestContext requestContext) throws ServiceException {
        String userName = requestContext.getParameter("userName");
        String inputPassword = requestContext.getParameter("inputPassword");
        String md5HexPassword = DigestUtils.md5Hex(inputPassword);
        String passwordByLogin = findPasswordByLogin(userName);
        return passwordByLogin.equals(md5HexPassword);
    }

    public User findUserByLogin(String login) throws ServiceException {
        try {
            return userDAO.findEntityByLogin(login).orElseThrow();
        } catch (DaoException e) {
            logger.warn("Service findUserByLogin error");
            throw new ServiceException("Service findUserByLogin error: " + e);
        }
    }

    public String findPasswordByLogin(String login) throws ServiceException {
        try {
            return userDAO.findPasswordByLogin(login).get();
        } catch (DaoException e) {
            logger.warn("Service findPasswordByLogin error");
            throw new ServiceException("Service findPasswordByLogin error: " + e);
        }
    }

    public boolean isExistUser(String login) throws ServiceException {
        try {
            return userDAO.isExistUser(login);
        } catch (DaoException e) {
            logger.warn("Service isExistUser error");
            throw new ServiceException("Service isExistUser error: " + e);
        }
    }

    @Override
    public boolean create(User user) throws ServiceException {
        try {
            return userDAO.create(user);
        } catch (DaoException e) {
            logger.warn("Service createUser error");
            throw new ServiceException("Service createUser error: " + e);
        }
    }

    @Override
    public boolean update(User user) throws ServiceException {
        try {
            return userDAO.update(user);
        } catch (DaoException e) {
            logger.warn("Service updateUser error");
            throw new ServiceException("Service updateUser error: " + e);
        }
    }

    public boolean updateWithoutPassword(User user) throws ServiceException {
        try {
            return userDAO.updateWithoutPassword(user);
        } catch (DaoException e) {
            logger.warn("Service updateWithoutPassword error");
            throw new ServiceException("Service updateWithoutPassword error: " + e);
        }
    }

    @Override
    public boolean delete(User user) throws ServiceException {
        try {
            return userDAO.delete(user);
        } catch (DaoException e) {
            logger.warn("Service updateWithoutPassword error");
            throw new ServiceException("Service updateWithoutPassword error: " + e);
        }
    }
}
