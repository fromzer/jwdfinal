package com.epam.jwd.listener;

import com.epam.jwd.pool.ConnectionPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Used for initializing connection pool when the application starts
 *
 * @author Egor Miheev
 * @version 1.0.0
 */
public class ApplicationContextListener implements ServletContextListener {
    private static final Logger logger = LoggerFactory.getLogger(ApplicationContextListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger.debug("Init connection pool");
        ConnectionPool.getInstance();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        logger.debug("Destroyed connection pool");
        ConnectionPool.getInstance().destroyConnection();
    }
}
