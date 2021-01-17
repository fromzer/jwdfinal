package com.epam.jwd.dao;

import com.epam.jwd.domain.impl.User;
import com.epam.jwd.domain.impl.UserRole;
import com.epam.jwd.exception.DaoException;
import com.epam.jwd.exception.UnknownEntityException;
import com.epam.jwd.pool.ConnectionPool;
import com.epam.jwd.pool.ProxyConnection;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

public class UserDAO extends AbstractDAO<Long, User> {
    private static final Logger logger = LoggerFactory.getLogger(UserDAO.class);
    private static final String SQL_SELECT_FIND_ALL =
            "SELECT u.id, u.login, u.first_name, u.last_name, u.email, u.role_id FROM app_user AS u ORDER BY u.first_name";
    private static final String SQL_SELECT_FIND_BY_ID =
            "SELECT u.id, u.login, u.first_name, u.last_name, u.email, u.role_id FROM app_user AS u WHERE u.id =?;";
    private static final String SQL_SELECT_FIND_BY_LOGIN =
            "SELECT u.id, u.login, u.first_name, u.last_name, u.email, u.role_id FROM app_user AS u WHERE u.login =?;";
    private static final String SQL_SELECT_FIND_LOGIN_BY_PASSWORD = "SELECT password FROM app_user WHERE login = ?";
    private static final String SQL_DELETE_ENTITY = "DELETE FROM app_user WHERE id =?;";
    private static final String SQL_FIND_USER = "SELECT id FROM app_user WHERE login = ?;";
    private static final String SQL_CREATE_ENTITY = "INSERT INTO app_user(login, first_name, last_name, password, email, role_id) VALUES (?,?,?,?,?,?);";
    private static final String SQL_UPDATE_ENTITY = "UPDATE app_user SET first_name=?, last_name=?, password=?, email=?, role_id=? WHERE id=?;";
    private static final String SQL_UPDATE_WITHOUT_PASSWORD_ENTITY = "UPDATE app_user SET first_name=?, last_name=?, email=?, role_id=? WHERE id=?;";
    private static final ReentrantLock reentrantLock = new ReentrantLock();
    private static final AtomicBoolean isCreated = new AtomicBoolean(false);
    private static ConnectionPool connectionPool = ConnectionPool.getInstance();
    private static UserDAO instance;

    private UserDAO() {
    }

    public static UserDAO getInstance() {
        if (!isCreated.get()) {
            reentrantLock.lock();
            try {
                if (instance == null) {
                    instance = new UserDAO();
                    isCreated.set(true);
                }
            } finally {
                reentrantLock.unlock();
            }
        }
        return instance;
    }

    public Optional<User> findEntityByLogin(String login) throws DaoException {
        User entity;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        ProxyConnection connection = ConnectionPool.getInstance().getConnection();
        try {
            statement = connection.prepareStatement(SQL_SELECT_FIND_BY_LOGIN);
            statement.setString(1, login);
            resultSet = statement.executeQuery();
            resultSet.next();
            entity = createEntity(resultSet);
        } catch (SQLException e) {
            logger.warn("Request findEntityByLogin execution error.");
            throw new DaoException("Error: ", e);
        } finally {
            close(resultSet);
            close(statement);
            close(connection);
        }
        logger.debug("Request find entity by login complete.");
        return Optional.ofNullable(entity);
    }

    @Override
    public boolean create(User entity) throws DaoException {
        boolean result;
        PreparedStatement statement = null;
        String md5HexPassword = DigestUtils.md5Hex(entity.getPassword());
        ProxyConnection connection = connectionPool.getConnection();
        try {
            statement = connection.prepareStatement(SQL_CREATE_ENTITY);
            statement.setString(1, entity.getLogin());
            statement.setString(2, entity.getFirstName());
            statement.setString(3, entity.getLastName());
            statement.setString(4, md5HexPassword);
            statement.setString(5, entity.getEmail());
            Long id = entity.getRole().getId();
            statement.setLong(6, id);
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
    public boolean update(User entity) throws DaoException {
        boolean result;
        PreparedStatement statement = null;
        ProxyConnection connection = connectionPool.getConnection();
        String md5HexPassword = DigestUtils.md5Hex(entity.getPassword());
        try {
            statement = connection.prepareStatement(SQL_UPDATE_ENTITY);
            statement.setString(1, entity.getFirstName());
            statement.setString(2, entity.getLastName());
            statement.setString(3, md5HexPassword);
            statement.setString(4, entity.getEmail());
            Long roleId = entity.getRole().getId();
            statement.setLong(5, roleId);
            statement.setLong(6, entity.getId());
            statement.executeUpdate();
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

    public boolean updateWithoutPassword(User entity) throws DaoException {
        boolean result;
        PreparedStatement statement = null;
        ProxyConnection connection = connectionPool.getConnection();
        try {
            statement = connection.prepareStatement(SQL_UPDATE_WITHOUT_PASSWORD_ENTITY);
            statement.setString(1, entity.getFirstName());
            statement.setString(2, entity.getLastName());
            statement.setString(3, entity.getEmail());
            Long roleId = entity.getRole().getId();
            statement.setLong(4, roleId);
            statement.setLong(5, entity.getId());
            statement.executeUpdate();
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

    public boolean isExistUser(String login) throws DaoException {
        boolean result;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        ProxyConnection connection = ConnectionPool.getInstance().getConnection();
        try {
            statement = connection.prepareStatement(SQL_FIND_USER, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            statement.setString(1, login);
            resultSet = statement.executeQuery();
            result = resultSet.first();
        } catch (SQLException e) {
            logger.warn("Request isExistUser execution error.");
            throw new DaoException("Error: ", e);
        } finally {
            close(resultSet);
            close(statement);
            close(connection);
        }
        logger.debug("Request isExistUser complete.");
        return result;
    }

    public Optional<String> findPasswordByLogin(String login) throws DaoException {
        String pass = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        ProxyConnection connection = ConnectionPool.getInstance().getConnection();
        try {
            statement = connection.prepareStatement(SQL_SELECT_FIND_LOGIN_BY_PASSWORD);
            statement.setString(1, login);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                pass = resultSet.getString(1);
            }
        } catch (SQLException e) {
            logger.warn("Request find password execution error.");
            throw new DaoException("Error: ", e);
        } finally {
            close(resultSet);
            close(statement);
            close(connection);
        }
        logger.debug("Request find password complete.");
        return Optional.ofNullable(pass);
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
    protected User createEntity(ResultSet resultSet) throws DaoException {
        User user = null;
        try {
            user = new User.Builder()
                    .setId(resultSet.getLong(1))
                    .setLogin(resultSet.getString(2))
                    .setFirstName(resultSet.getString(3))
                    .setLastName(resultSet.getString(4))
                    .setEmail(resultSet.getString(5))
                    .setRole(UserRole.resolveRoleById(resultSet.getLong(6)))
                    .build();
        } catch (UnknownEntityException e) {
            logger.warn("Error extract role");
        } catch (SQLException e) {
            logger.warn("Error create entity");
            throw new DaoException("Error:", e);
        }
        logger.debug("Create entity(User) complete.");
        return user;
    }
}
