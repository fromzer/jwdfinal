package com.epam.jwd.pool;

import com.epam.jwd.context.DatabaseProperties;
import com.epam.jwd.exception.ConfigurationException;
import com.epam.jwd.util.PropertyReaderUtil;
import com.mysql.jdbc.Driver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ConnectionPool is used for creating and managing DB connections
 *
 * @author Egor Miheev
 * @version 1.0.0
 */
public class ConnectionPool {
    private static final Logger logger = LoggerFactory.getLogger(ConnectionPool.class);
    private static final ReentrantLock reentrantLock = new ReentrantLock();

    /**
     * Flag indicates that the pool is created.
     */
    private static final AtomicBoolean isCreated = new AtomicBoolean(false);

    /**
     * Instance of ConnectionPool - Singleton
     */
    private static ConnectionPool instance;

    /**
     * DB connections storage
     */
    private BlockingQueue<ProxyConnection> connectionQueue;

    private ConnectionPool() {
        init();
    }


    /**
     * Get {@code ConnectionPool} instance
     *
     * @return instance of {@code ConnectionPool} class
     */
    public static ConnectionPool getInstance() {
        if (!isCreated.get()) {
            reentrantLock.lock();
            try {
                if (instance == null) {
                    instance = new ConnectionPool();
                    isCreated.set(true);
                }
            } finally {
                reentrantLock.unlock();
            }
        }
        return instance;
    }

    /**
     * Get {@code ProxyConnection} instance from pool storage
     *
     * @return instance of {@code ProxyConnection}
     */
    public ProxyConnection getConnection() {
        ProxyConnection connection = null;
        try {
            connection = connectionQueue.take();
        } catch (InterruptedException e) {
            logger.error("Failed to get connection from pool");
        }
        return connection;
    }

    /**
     * Close all connections in pool storage
     */
    public void destroyConnection() {
        for (int i = 0; i < connectionQueue.size(); i++) {
            try {
                connectionQueue.take().realClose();
            } catch (InterruptedException e) {
                logger.error("It is not possible to close the connection.");
            }
        }
    }


    /**
     * Return {@code ProxyConnection} instance back to pool storage
     *
     * @param connection to be returned
     */
    public void returnConnection(ProxyConnection connection) {
        if (connection != null) {
            connectionQueue.offer(connection);
        }
    }

    /**
     * Read ConnectionPool properties  and create connections for pool storage
     */
    private void init() {
        DatabaseProperties properties = PropertyReaderUtil.readDBProperties();
        String url = properties.getDbURL();
        String user = properties.getDbUser();
        String password = properties.getDbPassword();
        Integer poolSize = properties.getDbPoolSize();
        connectionQueue = new ArrayBlockingQueue<>(poolSize);
        for (int i = 0; i < poolSize; i++) {
            ProxyConnection connection = null;
            try {
                connection = new ProxyConnection(createConnection(url, user, password));
            } catch (SQLException e) {
                logger.error("Connection not created.");
            }
            if (connection != null) {
                connectionQueue.offer(connection);
            } else {
                throw new ConfigurationException("Connection refuse");
            }
        }
    }

    /**
     * Create connection instance
     */
    private Connection createConnection(String url, String user, String password) throws SQLException {
        DriverManager.registerDriver(new Driver());
        return DriverManager.getConnection(url, user, password);
    }
}
