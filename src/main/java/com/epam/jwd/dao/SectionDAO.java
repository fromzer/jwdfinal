package com.epam.jwd.dao;

import com.epam.jwd.domain.impl.Section;
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

public class SectionDAO extends AbstractDAO<Long, Section> {

    private static final Logger logger = LoggerFactory.getLogger(SectionDAO.class);
    private static final String SQL_SELECT_FIND_ALL = "SELECT sn.id, sn.title, sn.description, sn.conference_id FROM section sn";
    private static final String SQL_SELECT_FIND_BY_ID = "SELECT sn.id, sn.title, sn.description, sn.conference_id FROM section sn WHERE sn.id =?;";
    private static final String SQL_DELETE_ENTITY = "DELETE FROM section WHERE id =?;";
    private static final String SQL_CREATE_ENTITY = "INSERT INTO section(title, description, conference_id) VALUES (?, ?, ?);";
    private static final String SQL_UPDATE_ENTITY = "UPDATE section SET title=?, description=? WHERE id = ?";
    private static final String SQL_SELECT_FIND_BY_CONFERENCE_ID = "SELECT sn.id, sn.title, sn.description, sn.conference_id FROM section sn WHERE sn.conference_id = ?;";
    private static final ReentrantLock reentrantLock = new ReentrantLock();
    private static final AtomicBoolean isCreated = new AtomicBoolean(false);
    private static ConnectionPool connectionPool = ConnectionPool.getInstance();
    private static SectionDAO instance;

    private SectionDAO() {
    }

    public static SectionDAO getInstance() {
        if (!isCreated.get()) {
            reentrantLock.lock();
            try {
                if (instance == null) {
                    instance = new SectionDAO();
                    isCreated.set(true);
                }
            } finally {
                reentrantLock.unlock();
            }
        }
        return instance;
    }

    public List<Section> findEntityByConferenceId(Long id) throws DaoException {
        List<Section> sections = new ArrayList<>();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        ProxyConnection connection = ConnectionPool.getInstance().getConnection();
        try {
            statement = connection.prepareStatement(SQL_SELECT_FIND_BY_CONFERENCE_ID);
            statement.setLong(1, id);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                sections.add(createEntity(resultSet));
            }
        } catch (SQLException e) {
            logger.warn("Request findEntityById execution error.");
            throw new DaoException("Error: ", e);
        } finally {
            close(resultSet);
            close(statement);
            close(connection);
        }
        logger.debug("Request find entity by id complete.");
        return sections;
    }

    @Override
    public boolean create(Section entity) throws DaoException {
        boolean result;
        PreparedStatement statement = null;
        ProxyConnection connection = connectionPool.getConnection();
        try {
            statement = connection.prepareStatement(SQL_CREATE_ENTITY);
            statement.setString(1, entity.getTitle());
            statement.setString(2, entity.getDescription());
            statement.setLong(3, entity.getConferenceId());
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
    public boolean update(Section entity) throws DaoException {
        boolean result;
        PreparedStatement statement = null;
        ProxyConnection connection = connectionPool.getConnection();
        try {
            statement = connection.prepareStatement(SQL_UPDATE_ENTITY);
            statement.setString(1, entity.getTitle());
            statement.setString(2, entity.getDescription());
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
    protected Section createEntity(ResultSet resultSet) throws DaoException {
        Section section;
        try {
            section = new Section.Builder()
                    .setId(resultSet.getLong(1))
                    .setTitle(resultSet.getString(2))
                    .setDescription(resultSet.getString(3))
                    .setConferenceId(resultSet.getLong(4))
                    .build();
        } catch (SQLException e) {
            logger.warn("Error create entity");
            throw new DaoException("Error:", e);
        }
        logger.debug("Create entity complete: " + section.getClass().getName());
        return section;
    }
}
