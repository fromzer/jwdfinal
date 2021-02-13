package com.epam.jwd.dao;

import com.epam.jwd.domain.impl.Message;
import com.epam.jwd.exception.DaoException;
import com.epam.jwd.pool.ConnectionPool;
import com.epam.jwd.pool.ProxyConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

/**
 * DAO class used for working with Message objects and modifying data
 * in corresponding table
 *
 * @author Egor Miheev
 * @version 1.0.0
 */
public class MessageDAO extends AbstractDAO<Long, Message> {
    private static final Logger logger = LoggerFactory.getLogger(MessageDAO.class);
    private static final String SQL_SELECT_FIND_ALL_NEW = "SELECT m.id, m.topic, m.message, m.user_id, m.isAnswered FROM messages m WHERE m.isAnswered = false ORDER BY m.id DESC;";
    private static final String SQL_SELECT_FIND_BY_ID = "SELECT m.id, m.topic, m.message, m.user_id FROM messages m WHERE id = ?;";
    private static final String SQL_SELECT_FIND_BY_USER_ID = "SELECT m.id, m.topic, m.message, m.user_id FROM messages m WHERE user_id = ?;";
    private static final String SQL_DELETE_ENTITY = "DELETE FROM messages WHERE id =?;";
    private static final String SQL_CREATE_ENTITY = "INSERT INTO messages(topic, message, user_id) VALUES (?, ?, ?);";
    private static final String SQL_UPDATE_ENTITY = "UPDATE messages SET isAnswered=? WHERE id = ?;";
    private static final ReentrantLock reentrantLock = new ReentrantLock();
    private static final AtomicBoolean isCreated = new AtomicBoolean(false);
    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();
    private static MessageDAO instance;

    private MessageDAO() {
    }

    public static MessageDAO getInstance() {
        if (!isCreated.get()) {
            reentrantLock.lock();
            try {
                if (instance == null) {
                    instance = new MessageDAO();
                    isCreated.set(true);
                }
            } finally {
                reentrantLock.unlock();
            }
        }
        return instance;
    }

    @Override
    public boolean create(Message entity) throws DaoException {
        boolean result;
        try (ProxyConnection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_CREATE_ENTITY)) {
            statement.setString(1, entity.getTopic());
            statement.setString(2, entity.getDescription());
            statement.setLong(3, entity.getUserId());
            result = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.warn("Request create execution error.");
            throw new DaoException("Creation error: ", e);
        }
        logger.debug("Request create entity complete: " + entity.getClass().getName());
        return result;
    }

    @Override
    public boolean update(Message message) throws DaoException {
        boolean result;
        try (ProxyConnection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_ENTITY)) {
            statement.setBoolean(1, message.isAnswered());
            statement.setLong(2, message.getId());
            result = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.warn("Request update execution error.");
            throw new DaoException("Update error: ", e);
        }
        logger.debug("Request update state message complete");
        return result;
    }

    /**
     * Find all entries by id in table
     *
     * @param userId user for which messages are searched
     * @return List of messages
     * @throws DaoException if fail to find data in DB
     */
    public List<Message> findAllMessageByUserId(Long userId) throws DaoException {
        List<Message> entities = new ArrayList<>();
        try (ProxyConnection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_FIND_BY_USER_ID)) {
            statement.setLong(1, userId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                entities.add(createEntity(resultSet));
            }
        } catch (SQLException e) {
            logger.warn("Request find entity by id execution error.");
            throw new DaoException("Error: ", e);
        }
        logger.debug("Request find entity by id complete.");
        return entities;
    }

    @Override
    protected String getFindAllQuery() {
        return SQL_SELECT_FIND_ALL_NEW;
    }

    @Override
    protected String getFindByIdQuery() {
        return SQL_SELECT_FIND_BY_ID;
    }

    @Override
    protected String getDeleteQuery() {
        return SQL_DELETE_ENTITY;
    }

    @Override
    protected Message createEntity(ResultSet resultSet) throws DaoException {
        Message message;
        try {
            message = new Message.Builder()
                    .setId(resultSet.getLong(1))
                    .setTopic(resultSet.getString(2))
                    .setDescription(resultSet.getString(3))
                    .setUserId(resultSet.getLong(4))
                    .build();
        } catch (SQLException e) {
            logger.warn("Error create entity");
            throw new DaoException("Error:", e);
        }
        logger.debug("Create entity complete: " + message.getClass().getName());
        return message;
    }
}
