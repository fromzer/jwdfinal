package com.epam.jwd.dao;

import com.epam.jwd.domain.impl.State;
import com.epam.jwd.domain.impl.UserSection;
import com.epam.jwd.exception.DaoException;
import com.epam.jwd.exception.UnknownEntityException;
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
 * DAO class represents many to many relation between users and sections
 * used for working with UserSection objects and modifying data
 * in corresponding table
 *
 * @author Egor Miheev
 * @version 1.0.0
 */
public class UserSectionDAO extends AbstractDAO<Long, UserSection> {
    private static final Logger logger = LoggerFactory.getLogger(UserSectionDAO.class);
    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();
    private static final String SQL_SELECT_FIND_ALL = "SELECT us.id, us.user_id, us.section_id, us.state_id FROM users_sections us WHERE state_id = 1 ORDER BY us.id;";
    private static final String SQL_SELECT_FIND_BY_ID = "SELECT us.id, us.user_id, us.section_id, us.state_id FROM users_sections us WHERE us.id = ?;";
    private static final String SQL_SELECT_FIND_BY_USER_ID = "SELECT us.id, us.user_id, us.section_id, us.state_id FROM users_sections us WHERE us.user_id = ? ORDER BY us.id DESC;";
    private static final String SQL_DELETE_ENTITY = "DELETE FROM users_sections WHERE user_id = ? and section_id = ?;";
    private static final String SQL_CREATE_ENTITY = "INSERT INTO users_sections(user_id, section_id, state_id) VALUES (?, ?, ?);";
    private static final String SQL_UPDATE_ENTITY = "UPDATE users_sections SET user_id = ?, section_id = ?, state_id = ? WHERE id = ?;";
    private static final ReentrantLock reentrantLock = new ReentrantLock();
    private static final AtomicBoolean isCreated = new AtomicBoolean(false);
    private static UserSectionDAO instance;

    private UserSectionDAO() {
    }

    public static UserSectionDAO getInstance() {
        if (!isCreated.get()) {
            reentrantLock.lock();
            try {
                if (instance == null) {
                    instance = new UserSectionDAO();
                    isCreated.set(true);
                }
            } finally {
                reentrantLock.unlock();
            }
        }
        return instance;
    }

    @Override
    public boolean create(UserSection entity) throws DaoException {
        boolean result;
        PreparedStatement statement = null;
        ProxyConnection connection = connectionPool.getConnection();
        try {
            statement = connection.prepareStatement(SQL_CREATE_ENTITY);
            statement.setLong(1, entity.getUserId());
            statement.setLong(2, entity.getSectionId());
            statement.setLong(3, entity.getState().getId());
            result = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.warn("Request create execution error.");
            throw new DaoException("Creation error: ", e);
        } finally {
            close(statement);
            close(connection);
        }
        logger.debug("Request create entity complete: " + entity.getClass().getName());
        return result;
    }

    @Override
    public boolean update(UserSection entity) throws DaoException {
        boolean result;
        PreparedStatement statement = null;
        ProxyConnection connection = connectionPool.getConnection();
        try {
            statement = connection.prepareStatement(SQL_UPDATE_ENTITY);
            statement.setLong(1, entity.getUserId());
            statement.setLong(2, entity.getSectionId());
            statement.setLong(3, entity.getState().getId());
            statement.setLong(4, entity.getId());
            result = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.warn("Request update execution error.");
            throw new DaoException("Update error: ", e);
        } finally {
            close(statement);
            close(connection);
        }
        logger.debug("Request update entity complete: " + entity.getClass().getName());
        return result;
    }

    /**
     * Find all user's sections by user id
     *
     * @param id user for which sections are searched
     * @return List of user's sections
     * @throws DaoException if fail to find data in DB
     */
    public List<UserSection> findByUserId(Long id) throws DaoException {
        List<UserSection> userSectionList = new ArrayList<>();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        ProxyConnection connection = ConnectionPool.getInstance().getConnection();
        try {
            statement = connection.prepareStatement(SQL_SELECT_FIND_BY_USER_ID);
            statement.setLong(1, id);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                userSectionList.add(createEntity(resultSet));
            }
        } catch (SQLException e) {
            logger.warn("Request find execution error.");
            throw new DaoException("Error: ", e);
        } finally {
            close(resultSet);
            close(statement);
            close(connection);
        }
        logger.debug("Request find complete.");
        return userSectionList;
    }

    @Override
    protected String getFindAllQuery() {
        return SQL_SELECT_FIND_ALL;
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
    public boolean delete(UserSection entity) throws DaoException {
        boolean result;
        PreparedStatement statement = null;
        ProxyConnection connection = ConnectionPool.getInstance().getConnection();
        try {
            statement = connection.prepareStatement(getDeleteQuery());
            statement.setLong(1, entity.getUserId());
            statement.setLong(2, entity.getSectionId());
            result = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.warn("Request execution error.");
            throw new DaoException("Delete error: ", e);
        } finally {
            close(statement);
            close(connection);
        }
        logger.debug("Request delete entity complete: " + entity.toString());
        return result;
    }

    @Override
    protected UserSection createEntity(ResultSet resultSet) throws DaoException {
        UserSection userSection = null;
        try {
            userSection = new UserSection.Builder()
                    .setId(resultSet.getLong(1))
                    .setUserId(resultSet.getLong(2))
                    .setSectionId(resultSet.getLong(3))
                    .setState(State.resolveStateById(resultSet.getLong(4)))
                    .build();
        } catch (UnknownEntityException e) {
            logger.warn("Error extract state");
        } catch (SQLException e) {
            logger.warn("Error create entity");
            throw new DaoException("Error:", e);
        }
        logger.debug("Create entity(UserSection) complete.");
        return userSection;
    }
}
