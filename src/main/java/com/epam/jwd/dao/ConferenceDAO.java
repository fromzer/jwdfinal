package com.epam.jwd.dao;

import com.epam.jwd.domain.impl.Conference;
import com.epam.jwd.exception.DaoException;
import com.epam.jwd.pool.ConnectionPool;
import com.epam.jwd.pool.ProxyConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

public class ConferenceDAO extends AbstractDAO<Long, Conference> {
    private static final Logger logger = LoggerFactory.getLogger(ConferenceDAO.class);
    private static final String SQL_SELECT_FIND_ALL = "SELECT c.id, c.title, c.description, c.start_date, c.end_date FROM conference c ORDER BY c.id DESC";
    private static final String SQL_SELECT_FIND_BY_ID = "SELECT c.id, c.title, c.description, c.start_date, c.end_date FROM conference c WHERE c.id=?";
    private static final String SQL_SELECT_FIND_LIMIT = "SELECT c.id, c.title, c.description, c.start_date, c.end_date FROM conference c ORDER BY c.id DESC LIMIT ?, ?;";
    private static final String SQL_SELECT_COUNT = "SELECT COUNT(id) FROM conference;";
    private static final String SQL_DELETE_ENTITY = "DELETE FROM conference WHERE id =?;";
    private static final String SQL_CREATE_ENTITY = "INSERT INTO conference (title, description, start_date, end_date) VALUES (?, ?, ?, ?);";
    private static final String SQL_UPDATE_ENTITY = "UPDATE conference SET title=?, description=?, start_date=?, end_date=? WHERE id=?;";
    private static final ReentrantLock reentrantLock = new ReentrantLock();
    private static final AtomicBoolean isCreated = new AtomicBoolean(false);
    private static ConferenceDAO instance;
    private static ConnectionPool connectionPool = ConnectionPool.getInstance();

    private ConferenceDAO() {
    }

    public static ConferenceDAO getInstance() {
        if (!isCreated.get()) {
            reentrantLock.lock();
            try {
                if (instance == null) {
                    instance = new ConferenceDAO();
                    isCreated.set(true);
                }
            } finally {
                reentrantLock.unlock();
            }
        }
        return instance;
    }

    @Override
    public boolean create(Conference entity) throws DaoException {
        boolean result;
        PreparedStatement statement = null;
        ProxyConnection connection = connectionPool.getConnection();
        try {
            statement = connection.prepareStatement(SQL_CREATE_ENTITY);
            statement.setString(1, entity.getTitle());
            statement.setString(2, entity.getDescription());
            statement.setDate(3, Date.valueOf(entity.getDateStart()));
            statement.setDate(4, Date.valueOf(entity.getDateEnd()));
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
    public boolean update(Conference entity) throws DaoException {
        boolean result;
        PreparedStatement statement = null;
        ProxyConnection connection = connectionPool.getConnection();
        try {
            statement = connection.prepareStatement(SQL_UPDATE_ENTITY);
            statement.setString(1, entity.getTitle());
            statement.setString(2, entity.getDescription());
            statement.setDate(3, Date.valueOf(entity.getDateStart()));
            statement.setDate(4, Date.valueOf(entity.getDateEnd()));
            statement.setLong(5, entity.getId());
            result = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.warn("Request update user execution error.");
            throw new DaoException("Update error: ", e);
        } finally {
            close(statement);
            close(connection);
        }
        logger.debug("Request update entity complete: " + entity.getClass().getName());
        return result;
    }

    public Long findAmountOfConferences() throws DaoException {
        long result;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        ProxyConnection connection = ConnectionPool.getInstance().getConnection();
        try {
            statement = connection.prepareStatement(SQL_SELECT_COUNT);
            resultSet = statement.executeQuery();
            resultSet.next();
            result = resultSet.getLong(1);
        } catch (SQLException e) {
            logger.warn("Request count execution error.");
            throw new DaoException("Error: ", e);
        } finally {
            close(resultSet);
            close(statement);
            close(connection);
        }
        logger.debug("Request find count complete.");
        return result;
    }

    public List<Conference> findConferenceByLimit(Long position, Long records) throws DaoException {
        List<Conference> entities = new ArrayList<>();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        ProxyConnection connection = ConnectionPool.getInstance().getConnection();
        try {
            statement = connection.prepareStatement(SQL_SELECT_FIND_LIMIT);
            statement.setLong(1, position);
            statement.setLong(2, records);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                entities.add(createEntity(resultSet));
            }
        } catch (SQLException e) {
            logger.warn("Request findAll execution error.", e);
            throw new DaoException("Error: ", e);
        } finally {
            close(resultSet);
            close(statement);
            close(connection);
        }
        logger.debug("Request find conference by limit complete.");
        return entities;
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
    protected Conference createEntity(ResultSet resultSet) throws DaoException {
        Conference conference;
        try {
            conference = new Conference.Builder()
                    .setId(resultSet.getLong(1))
                    .setTitle(resultSet.getString(2))
                    .setDescription(resultSet.getString(3))
                    .setDateStart(resultSet.getDate(4).toLocalDate())
                    .setDateEnd(resultSet.getDate(5).toLocalDate())
                    .build();
        } catch (SQLException e) {
            logger.warn("Error create entity");
            throw new DaoException("Error:", e);
        }
        logger.debug("Create entity complete: " + conference.getClass().getName());
        return conference;
    }
}