package com.epam.jwd.service.impl;

import com.epam.jwd.dao.MessageDAO;
import com.epam.jwd.domain.impl.Message;
import com.epam.jwd.exception.DaoException;
import com.epam.jwd.exception.ServiceException;
import com.epam.jwd.service.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Service class is used for working with Message objects via DAO layer
 * classes
 *
 * @author Egor Miheev
 * @version 1.0.0
 */
public class MessageService implements Service<Message> {
    private static final Logger logger = LoggerFactory.getLogger(MessageService.class);
    private static final ReentrantLock reentrantLock = new ReentrantLock();
    private static final AtomicBoolean isCreated = new AtomicBoolean(false);
    private static MessageService instance;
    private final MessageDAO messageDAO = MessageDAO.getInstance();

    private MessageService() {
    }

    public static MessageService getInstance() {
        if (!isCreated.get()) {
            reentrantLock.lock();
            try {
                if (instance == null) {
                    instance = new MessageService();
                    isCreated.set(true);
                }
            } finally {
                reentrantLock.unlock();
            }
        }
        return instance;
    }

    /**
     * Get all messages
     *
     * @return List of Messages
     * @throws ServiceException if fail to retrieve data from DB
     */
    public List<Message> findAllNewMessage() throws ServiceException {
        try {
            return messageDAO.findAll();
        } catch (DaoException e) {
            logger.warn("Service findAllMessage error");
            throw new ServiceException("Service findAllMessage error: " + e);
        }
    }

    @Override
    public boolean create(Message message) throws ServiceException {
        try {
            return messageDAO.create(message);
        } catch (DaoException e) {
            logger.warn("Service createMessage error");
            throw new ServiceException("Service createMessage error: " + e);
        }
    }

    @Override
    public boolean update(Message message) throws ServiceException {
        try {
            return messageDAO.update(message);
        } catch (DaoException e) {
            logger.warn("Service updateStateMessage error");
            throw new ServiceException("Service updateStateMessage error: " + e);
        }
    }

    @Override
    public boolean delete(Message message) throws ServiceException {
        try {
            return messageDAO.delete(message);
        } catch (DaoException e) {
            logger.warn("Service deleteMessage error");
            throw new ServiceException("Service deleteMessage error: " + e);
        }
    }


}
