package com.epam.jwd.util;

import com.epam.jwd.context.DatabaseProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ResourceBundle;

/**
 * Util for reading DB properties
 *
 * @author Egor Miheev
 * @version 1.0.0
 */
public class PropertyReaderUtil {
    private static Logger logger = LoggerFactory.getLogger(PropertyReaderUtil.class);

    private PropertyReaderUtil() {
    }

    /**
     * Read DB properties
     */
    public static DatabaseProperties readDBProperties() {
        ResourceBundle resource = ResourceBundle.getBundle("database");
        String dbURL = resource.getString("db.url");
        String dbUser = resource.getString("db.user");
        String dbPassword = resource.getString("db.password");
        Integer dbPoolSize = Integer.parseInt(resource.getString("db.poolSize"));
        DatabaseProperties databaseProperties = new DatabaseProperties(dbURL, dbUser, dbPassword, dbPoolSize);
        return databaseProperties;
    }
}
