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

/**
 * Base abstract class for DAO classes
 *
 * @author Egor Miheev
 * @version 1.0.0
 */
public abstract class AbstractDAO<K, T extends BaseEntity> {
    private static final Logger logger = LoggerFactory.getLogger(AbstractDAO.class);

    /**
     * Create entry in DB
     *
     * @param entity an entity of business model
     * @return true if add DB table entry
     * @throws DaoException if fail to retrieve data from DB
     */
    public abstract boolean create(T entity) throws DaoException;

    /**
     * Update entry in DB
     *
     * @param entity an entity of business model
     * @return true if entry updated
     * @throws DaoException if fail to update data in DB
     */
    public abstract boolean update(T entity) throws DaoException;

    /**
     * Find all entries in table
     *
     * @return List of entities
     * @throws DaoException if fail to retrieve data from DB
     */
    public List<T> findAll() throws DaoException {
        List<T> entities = new ArrayList<>();
        try (ProxyConnection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(getFindAllQuery());
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                entities.add(createEntity(resultSet));
            }
        } catch (SQLException e) {
            logger.warn("Request findAll execution error.");
            throw new DaoException("Error: ", e);
        }
        logger.debug("Statement findAll complete");
        return entities;
    }

    /**
     * Find entry in table
     *
     * @return Optional entry
     * @throws DaoException if fail to retrieve data from DB
     */
    public Optional<T> findEntityById(K id) throws DaoException {
        T entity;
        try (ProxyConnection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(getFindByIdQuery())) {
            statement.setLong(1, (Long) id);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            entity = createEntity(resultSet);
        } catch (SQLException e) {
            logger.warn("Request find entity by id execution error.");
            throw new DaoException("Error: ", e);
        }
        logger.debug("Request find entity by id complete");
        return Optional.of(entity);
    }

    /**
     * Delete entry in table
     *
     * @return true if entry deleted
     * @throws DaoException if error is occurred during SQL command execution
     */
    public boolean delete(T entity) throws DaoException {
        boolean result;
        try (ProxyConnection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(getDeleteQuery())) {
            statement.setLong(1, entity.getId());
            result = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.warn("Request execution error.");
            throw new DaoException("Delete error: ", e);
        }
        logger.debug("Request delete entity complete: " + entity.toString());
        return result;
    }

    /**
     * Get sql statement that helps find all entries in table
     *
     * @return sql statement
     */
    protected abstract String getFindAllQuery();

    /**
     * Get sql statement that helps find entry by id in table
     *
     * @return sql statement
     */
    protected abstract String getFindByIdQuery();

    /**
     * Get sql statement that helps delete entry in table
     *
     * @return sql statement
     */
    protected abstract String getDeleteQuery();

    /**
     * Get sql statement that helps find entry by id in table
     *
     * @return sql statement
     */
    protected abstract T createEntity(ResultSet resultSet) throws DaoException;
}
