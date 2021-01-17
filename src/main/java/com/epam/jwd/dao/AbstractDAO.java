package com.epam.jwd.dao;

import com.epam.jwd.domain.BaseEntity;
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
import java.util.Optional;

public abstract class AbstractDAO<K, T extends BaseEntity> {
    private static final Logger logger = LoggerFactory.getLogger(AbstractDAO.class);

    public abstract boolean create(T entity) throws DaoException;

    public abstract boolean update(T entity) throws DaoException;

    public List<T> findAll() throws DaoException {
        List<T> entities = new ArrayList<>();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        ProxyConnection connection = ConnectionPool.getInstance().getConnection();
        try {
            statement = connection.prepareStatement(getFindAllQuery());
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                entities.add(createEntity(resultSet));
            }
        } catch (SQLException e) {
            logger.warn("Request findAll execution error.");
            throw new DaoException("Error: ", e);
        } finally {
            close(resultSet);
            close(statement);
            close(connection);
        }
        logger.debug("Statement findAll complete");
        return entities;
    }

    public Optional<T> findEntityById(K id) throws DaoException {
        T entity;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        ProxyConnection connection = ConnectionPool.getInstance().getConnection();
        try {
            statement = connection.prepareStatement(getFindByIdQuery());
            statement.setLong(1, (Long) id);
            resultSet = statement.executeQuery();
            resultSet.next();
            entity = createEntity(resultSet);
        } catch (SQLException e) {
            logger.warn("Request find entity by id execution error.");
            throw new DaoException("Error: ", e);
        } finally {
            close(resultSet);
            close(statement);
            close(connection);
        }
        logger.debug("Request find entity by id complete");
        return Optional.of(entity);
    }

    public boolean delete(T entity) throws DaoException {
        boolean result;
        PreparedStatement statement = null;
        ProxyConnection connection = ConnectionPool.getInstance().getConnection();
        try {
            statement = connection.prepareStatement(getDeleteQuery());
            statement.setLong(1, entity.getId());
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

    protected abstract String getFindAllQuery();

    protected abstract String getFindByIdQuery();

    protected abstract String getDeleteQuery();

    protected abstract T createEntity(ResultSet resultSet) throws DaoException;

    public void close(PreparedStatement st) {
        try {
            if (st != null) {
                st.close();
            }
        } catch (SQLException e) {
            logger.warn("Failed to close prepared statement.");
        }
    }

    public void close(ProxyConnection connection) {
        if (connection != null) {
            connection.close();
        }
    }

    public void close(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                logger.warn("Failed to close result set.");
            }
        }
    }
}
